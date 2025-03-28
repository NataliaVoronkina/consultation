package com.heliosx.consultation.application.mapper;

import com.heliosx.consultation.domain.Answer;
import com.heliosx.consultation.domain.Consultation;
import com.heliosx.consultation.domain.Question;
import com.heliosx.consultation.presentation.dto.AnswerDto;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public Answer mapAnswerDto(AnswerDto answerDto, Consultation consultation, Question question) {
        return Answer.builder()
                .consultation(consultation)
                .question(question)
                .answerText(answerDto.getAnswerText())
                .build();
    }

}
