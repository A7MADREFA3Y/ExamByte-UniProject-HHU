package org.example.exambyte.domain.model;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

//    @ManyToOne
//    @JoinColumn(name = "test_id", nullable = false)
//    private Test test;

    @JoinColumn(name = "test_id", nullable = false)
    private Long testId;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_text")
    private List<String> options;

    @Column(name = "correct_answer")
    private String correctAnswer;
}
