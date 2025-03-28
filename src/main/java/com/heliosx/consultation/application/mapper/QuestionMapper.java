package com.heliosx.consultation.application.mapper;

import com.heliosx.consultation.domain.Question;
import com.heliosx.consultation.presentation.dto.QuestionDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public QuestionDto mapQuestion(Question question) {
        return QuestionDto.builder()
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType())
                .build();
    }

}
