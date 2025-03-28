package com.heliosx.consultation.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnswerDto {

    @NotNull
    private final UUID consultationUid;

    @NotNull
    private final Long questionId;

    @NotBlank
    private final String answerText;

}
