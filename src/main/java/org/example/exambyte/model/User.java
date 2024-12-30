package org.example.exambyte.model;


import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "github_username", unique = true)
    private String githubUsername;

    @Column(name = "github_id", unique = true)
    private String githubId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "takenBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestResult> testResults;
}