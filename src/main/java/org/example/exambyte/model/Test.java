package org.example.exambyte.model;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_name", unique = true)
    private String testName;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "result_publication_time")
    private LocalDateTime resultPublicationTime;

    @Column(name = "created_by")
    private String createdBy;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestResult> results;



    public Test(Long id, String testName) {
        this.id = id;
        this.testName = testName;
    }

    public Long getId() {
        return id;
    }

    public String getTestName() {
        return testName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getResultPublicationTime() {
        return resultPublicationTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<TestResult> getResults() {
        return results;
    }



}
