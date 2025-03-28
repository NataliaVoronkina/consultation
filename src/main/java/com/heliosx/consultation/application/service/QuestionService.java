package com.heliosx.consultation.application.service;

import com.heliosx.consultation.domain.Question;
import com.heliosx.consultation.infrastructure.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find question with uid [" + questionId + "]"));
    }

}
