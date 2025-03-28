package com.heliosx.consultation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String questionText;

    @Column(nullable = false)
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Answer answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionText, question.questionText) && questionType == question.questionType && Objects.equals(consultation, question.consultation) && Objects.equals(answer, question.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, questionType, consultation, answer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", questionType=" + questionType +
                ", consultation=" + consultation +
                ", answer=" + answer +
                '}';
    }
}
