package com.heliosx.consultation.application.service;

import com.heliosx.consultation.application.mapper.AnswerMapper;
import com.heliosx.consultation.application.mapper.ConsultationDecisionMapper;
import com.heliosx.consultation.application.mapper.QuestionMapper;
import com.heliosx.consultation.domain.*;
import com.heliosx.consultation.infrastructure.*;
import com.heliosx.consultation.presentation.dto.AnswerDto;
import com.heliosx.consultation.presentation.dto.ConsultationDecisionDto;
import com.heliosx.consultation.presentation.dto.QuestionDto;
import com.heliosx.consultation.types.ConsultationUid;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final CustomerRepository customerRepository;
    private final ConsultationRepository consultationRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final ConsultationDecisionRepository consultationDecisionRepository;
    private final ConsultationDecisionMapper consultationDecisionMapper;

    @Transactional
    public ConsultationUid startConsultation(UUID customerUid) {
        ConsultationUid consultationUid = new ConsultationUid();

        Customer customer = customerRepository.findByCustomerUid(customerUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with uuid %s not found", customerUid)));

        Consultation consultation = Consultation.builder()
                .consultationUid(consultationUid.getValue())
                .customer(customer)
                .build();

        Question question1 = Question.builder()
                .questionText("Are you allergic?")
                .questionType(QuestionType.BOOLEAN)
                .consultation(consultation)
                .build();
        Question question2 = Question.builder()
                .questionText("Are you currently taking any medication?")
                .questionType(QuestionType.BOOLEAN)
                .consultation(consultation)
                .build();

        consultation.setQuestions(List.of(question1, question2));

        consultationRepository.save(consultation);
        log.info("Consultation {} started for customer {}", consultation.getConsultationUid(), customerUid);

        return consultationUid;
    }

    public List<QuestionDto> getQuestions(UUID consultationUid) {
        List<Question> questions = questionRepository.findQuestionsByConsultationUid(consultationUid);
        return questions.stream()
                .map(questionMapper::mapQuestion)
                .toList();
    }

    public void saveAnswers(UUID consultationUid, List<AnswerDto> answers) {
        Consultation consultation = consultationRepository.findByConsultationUid(consultationUid)
                .orElseThrow(() -> new EntityNotFoundException("Consultation with uuid " + consultationUid + " not found"));

        //todo check if already persisted
        List<Answer> answersToPersist = answers.stream()
                .map(answerDto -> {
                    Question question = questionService.findQuestionById(answerDto.getQuestionId());
                    return answerMapper.mapAnswerDto(answerDto, consultation, question);
                })
                .toList();

        answerRepository.saveAll(answersToPersist);
        log.info("Answers for consultation {} saved", consultationUid);

        // todo Here should be some workflow to make a consultation decision. Ex: send Questions Answered Even to message queue to review by a doctor
        makeConsultationDecision(consultation);
    }

    public void makeConsultationDecision(Consultation consultation) {
        // todo Ideally this should be done in separate service
        ConsultationDecision decision = ConsultationDecision.builder()
                .decisionUid(UUID.randomUUID())
                .decision("Prescription confirmed")
                .consultation(consultation)
                .build();

        consultationDecisionRepository.save(decision);
        log.info("Consultation decision {} made for consultation {}", consultation.getConsultationUid(), consultation.getConsultationUid());
    }

    public ConsultationDecisionDto getConsultationDecision(UUID consultationUid) {
        ConsultationDecision consultationDecision = consultationDecisionRepository.findConsultationDecision(consultationUid)
                .orElseThrow(() -> new EntityNotFoundException("ConsultationDecision [" + consultationUid + "] not found"));
        return consultationDecisionMapper.mapConsultationDecision(consultationDecision);
    }

}
