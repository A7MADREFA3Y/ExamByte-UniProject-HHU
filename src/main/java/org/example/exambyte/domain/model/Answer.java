package org.example.exambyte.domain.model;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "question_id", nullable = false)
//    private Question question;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "taken_By", nullable = false)
    private String takenBy;

    @Column(name = "test_id", nullable = false)
    private Long testId;

//    @ManyToOne
//    @JoinColumn(name = "test_result_id")
//    private TestResult testResult;
}
