package com.heliosx.consultation.application.mapper;

import com.heliosx.consultation.domain.ConsultationDecision;
import com.heliosx.consultation.presentation.dto.ConsultationDecisionDto;
import org.springframework.stereotype.Component;

@Component
public class ConsultationDecisionMapper {

    public ConsultationDecisionDto mapConsultationDecision(ConsultationDecision consultationDecision) {
        return ConsultationDecisionDto.builder()
                .id(consultationDecision.getDecisionUid())
                .decision(consultationDecision.getDecision())
                .build();
    }

}
