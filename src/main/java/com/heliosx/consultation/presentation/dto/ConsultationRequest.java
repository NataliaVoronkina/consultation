package com.heliosx.consultation.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ConsultationRequest(@NotNull UUID customerUid) {

}
