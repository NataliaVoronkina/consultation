package com.heliosx.consultation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "answer", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"consultation_id", "question_id"})
})
public class Answer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @OneToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String answerText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(consultation, answer.consultation) && Objects.equals(question, answer.question) && Objects.equals(answerText, answer.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consultation, question, answerText);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", consultation=" + consultation +
                ", question=" + question +
                ", answerText='" + answerText + '\'' +
                '}';
    }
}
