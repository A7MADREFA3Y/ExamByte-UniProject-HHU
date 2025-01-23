package org.example.exambyte.infrasructure.repositoryImp.test;

import org.example.exambyte.domain.model.Test;
import org.example.exambyte.domain.repository.TestRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestRepositoryImp implements TestRepository {

    private final JpaTestRepository jpaTestRepository;

    public TestRepositoryImp(JpaTestRepository jpaTestRepository) {
        this.jpaTestRepository = jpaTestRepository;
    }

    @Override
    public void saveTest(Test test) {
        jpaTestRepository.save(test);

    }

    @Override
    public void deleteById(Long testId) {
        jpaTestRepository.deleteById(testId);
    }

    @Override
    public Test findById(Long testId) {
        Optional<Test> byId = jpaTestRepository.findById(testId);
        return byId.orElse(null);
    }

    @Override
    public List<Test> findAll() {
        return jpaTestRepository.findAll();
    }



}
