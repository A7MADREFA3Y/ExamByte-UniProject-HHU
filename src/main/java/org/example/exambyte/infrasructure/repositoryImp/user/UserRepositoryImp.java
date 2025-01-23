package org.example.exambyte.infrasructure.repositoryImp.user;

import org.example.exambyte.domain.model.User;
import org.example.exambyte.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImp(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }



    @Override
    public Optional<User> findByGithubId(String githubId) {
        return jpaUserRepository.findByGithubId(githubId);
    }



    @Override
    public User save(User newUser) {
        return     jpaUserRepository.save(newUser);
    }


}
