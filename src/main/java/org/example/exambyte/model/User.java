package org.example.exambyte.model;


import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "github_id", unique = true)
    private String githubId;

    @Column(name = "github_username", unique = true)
    private String githubUsername;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "takenBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestResult> testResults;
}