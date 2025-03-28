package com.heliosx.consultation.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ConsultationDecisionDto {

    private final UUID id;

    private final String decision;

}
