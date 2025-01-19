package org.example.exambyte.domain.model;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
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

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "result_publication_time", nullable = false)
    private LocalDateTime resultPublicationTime;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    public Test(Long id, String testName) {
        this.id = id;
        this.testName = testName;
    }


}
