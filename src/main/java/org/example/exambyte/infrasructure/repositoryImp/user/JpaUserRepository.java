package org.example.exambyte.infrasructure.repositoryImp.user;

import org.example.exambyte.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User , Long> {
    Optional<User> findByGithubId(String githubId);
}
