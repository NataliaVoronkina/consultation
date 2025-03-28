package com.heliosx.consultation.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heliosx.consultation.domain.*;
import com.heliosx.consultation.infrastructure.*;
import com.heliosx.consultation.presentation.dto.AnswerDto;
import com.heliosx.consultation.presentation.dto.ConsultationDecisionDto;
import com.heliosx.consultation.types.ConsultationUid;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ConsultationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ConsultationDecisionRepository consultationDecisionRepository;

    @BeforeEach
    void clearDatabase() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        consultationDecisionRepository.deleteAll();
        consultationRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void shouldStartConsultation() throws Exception {
        // given when
        ConsultationUid consultationUid = startConsultation();

        // then
        Consultation consultation = consultationRepository.findByConsultationUid(consultationUid.getValue()).orElseThrow();
        assertThat(consultation.getCustomer().getCustomerUid().toString()).isEqualTo("37a624d6-efc2-4b8a-b1c2-88503c54ee18");
        assertThat(consultation.getCustomer().getName()).isEqualTo("John");
        assertThat(consultation.getQuestions().size()).isEqualTo(2);
        assertThat(consultation.getQuestions().get(0).getQuestionText()).isEqualTo("Are you allergic?");
        assertThat(consultation.getQuestions().get(0).getQuestionType()).isEqualTo(QuestionType.BOOLEAN);
        assertThat(consultation.getQuestions().get(1).getQuestionText()).isEqualTo("Are you currently taking any medication?");
        assertThat(consultation.getQuestions().get(1).getQuestionType()).isEqualTo(QuestionType.BOOLEAN);
        assertThat(consultation.getAnswers()).isNull();
    }

    @Test
    void shouldReturnTheListOfQuestions() throws Exception {
        // given
        ConsultationUid consultationUid = startConsultation();

        // when then
        mockMvc.perform(get("/api/v1/consultation/" + consultationUid.getValue() + "/questions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].questionText").value("Are you allergic?"))
                .andExpect(jsonPath("$[0].questionType").value(QuestionType.BOOLEAN.toString()))
                .andExpect(jsonPath("$[1].questionText").value("Are you currently taking any medication?"))
                .andExpect(jsonPath("$[1].questionType").value(QuestionType.BOOLEAN.toString()))
                .andReturn();
    }

    @Test
    void shouldReceiveAnswersAndSave() throws Exception {
        // given
        ConsultationUid consultationUid = startConsultation();

        // when
        receiveResponseAndSaveAnswers(consultationUid);

        // then
        List<Answer> answers = answerRepository.findAnswersByConsultationUid(consultationUid.getValue());
        assertThat(answers.size()).isEqualTo(2);
        assertThat(answers.get(0).getConsultation().getConsultationUid()).isEqualTo(consultationUid.getValue());
        assertThat(answers.get(0).getQuestion().getQuestionText()).isEqualTo("Are you allergic?");
        assertThat(answers.get(0).getAnswerText()).isEqualTo("No");

        assertThat(answers.get(1).getConsultation().getConsultationUid()).isEqualTo(consultationUid.getValue());
        assertThat(answers.get(1).getQuestion().getQuestionText()).isEqualTo("Are you currently taking any medication?");
        assertThat(answers.get(1).getAnswerText()).isEqualTo("Yes");
    }

    @Test
    public void shouldReturnConsultationDecision() throws Exception {
        // given
        ConsultationUid consultationUid = startConsultation();
        receiveResponseAndSaveAnswers(consultationUid);

        // when then
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/consultation/decision/" + consultationUid.getValue().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        ConsultationDecisionDto consultationDecisionResponse = objectMapper.readValue(response.getContentAsString(), ConsultationDecisionDto.class);

        ConsultationDecision consultationDecision = consultationDecisionRepository.findConsultationDecision(consultationUid.getValue()).orElseThrow();
        assertThat(consultationDecision.getDecision()).isEqualTo(consultationDecisionResponse.getDecision());
        assertThat(consultationDecision.getConsultation().getConsultationUid()).isEqualTo(consultationUid.getValue());
    }

    private void receiveResponseAndSaveAnswers(ConsultationUid consultationUid) throws Exception {
        List<Question> questions = questionRepository.findQuestionsByConsultationUid(consultationUid.getValue());

        AnswerDto answer1 = AnswerDto.builder()
                .consultationUid(consultationUid.getValue())
                .questionId(questions.get(0).getId())
                .answerText("No")
                .build();

        AnswerDto answer2 = AnswerDto.builder()
                .consultationUid(consultationUid.getValue())
                .questionId(questions.get(1).getId())
                .answerText("Yes")
                .build();

        List<AnswerDto> answerDtos = List.of(answer1, answer2);
        String answersContext = objectMapper.writeValueAsString(answerDtos);

        mockMvc.perform(post("/api/v1/consultation/" + consultationUid.getValue() + "/answers")
                        .contentType("application/json")
                        .content(answersContext))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private ConsultationUid startConsultation() throws Exception {
        UUID customerUid = UUID.fromString("37a624d6-efc2-4b8a-b1c2-88503c54ee18");
        Customer customer = Customer.builder()
                .customerUid(customerUid)
                .name("John")
                .build();
        customerRepository.save(customer);

        String customerUidContext = objectMapper.writeValueAsString(customerUid);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/consultation/create")
                        .contentType("application/json")
                        .content(customerUidContext))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        return objectMapper.readValue(response.getContentAsString(), ConsultationUid.class);
    }

}