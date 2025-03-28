package com.heliosx.consultation.presentation.dto;

import com.heliosx.consultation.domain.QuestionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionDto {

    private final String questionText;

    private final QuestionType questionType;

}
