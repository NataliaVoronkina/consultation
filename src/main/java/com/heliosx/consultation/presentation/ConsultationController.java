package com.heliosx.consultation.presentation;

import com.heliosx.consultation.application.service.ConsultationService;
import com.heliosx.consultation.presentation.dto.AnswerDto;
import com.heliosx.consultation.presentation.dto.ConsultationDecisionDto;
import com.heliosx.consultation.presentation.dto.QuestionDto;
import com.heliosx.consultation.types.ConsultationUid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping("/create")
    public ConsultationUid startConsultation(@Valid @RequestBody UUID customerUid) {
        return consultationService.startConsultation(customerUid);
    }

    @GetMapping("/{consultationUid}/questions")
    public List<QuestionDto> getQuestions(@PathVariable UUID consultationUid) {
        return consultationService.getQuestions(consultationUid);
    }

    @PostMapping("/{consultationUid}/answers")
    public void addAnswers(@PathVariable UUID consultationUid, @Valid @RequestBody List<AnswerDto> answers) {
        consultationService.saveAnswers(consultationUid, answers);
    }

    @GetMapping("/decision/{consultationUid}")
    public ConsultationDecisionDto getConsultationDecision(@PathVariable UUID consultationUid) {
        return consultationService.getConsultationDecision(consultationUid);
    }

}
