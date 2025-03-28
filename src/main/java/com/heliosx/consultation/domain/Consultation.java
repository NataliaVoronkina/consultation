package com.heliosx.consultation.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID consultationUid;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Answer> answers;

    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL)
    private ConsultationDecision consultationDecision;

    @CreationTimestamp
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consultation that = (Consultation) o;
        return Objects.equals(consultationUid, that.consultationUid) && Objects.equals(customer, that.customer) && Objects.equals(questions, that.questions) && Objects.equals(answers, that.answers) && Objects.equals(consultationDecision, that.consultationDecision) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consultationUid, customer, questions, answers, consultationDecision, createdAt);
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", consultationUid=" + consultationUid +
                ", customer=" + customer +
                ", questions=" + questions +
                ", answers=" + answers +
                ", consultationDecision=" + consultationDecision +
                ", createdAt=" + createdAt +
                '}';
    }
}
