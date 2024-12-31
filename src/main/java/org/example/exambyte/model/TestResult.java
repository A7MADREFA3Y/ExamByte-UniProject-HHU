package org.example.exambyte.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_results")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "taken_by", nullable = false)
    private User takenBy;

    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @Column(name = "submit_date", nullable = false)
    private LocalDateTime submitDate;

    @Column(name = "grade")
    private Double grade;

    @Column(name = "passed")
    private Boolean passed;

    @Column(name = "graded", nullable = false)
    private Boolean graded = false;

    @Column(name = "corrected_by")
    private String correctedBy;

    @PrePersist
    public void prePersist() {
        if (submitDate == null) {
            submitDate = LocalDateTime.now();
        }
    }
}
