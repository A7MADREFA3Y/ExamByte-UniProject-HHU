package org.example.exambyte.domain.repository;

import org.example.exambyte.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByGithubId(String githubId);

    User save(User newUser);

}