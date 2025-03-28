package com.heliosx.consultation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ConsultationDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID decisionUid;

    @Column(nullable = false)
    private String decision;

    @OneToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultationDecision that = (ConsultationDecision) o;
        return Objects.equals(decisionUid, that.decisionUid) && Objects.equals(decision, that.decision) && Objects.equals(consultation, that.consultation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(decisionUid, decision, consultation);
    }

    @Override
    public String toString() {
        return "ConsultationDecision{" +
                "id=" + id +
                ", decisionUid=" + decisionUid +
                ", decision='" + decision + '\'' +
                ", consultation=" + consultation +
                '}';
    }
}
