package org.example.exambyte.infrasructure.repositoryImp.testResult;

import org.example.exambyte.domain.model.TestResult;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TestResultRepositoryImp implements TestResultRepository {
    private final JpaTestResultRepository jpaTestResultRepository;

    public TestResultRepositoryImp(JpaTestResultRepository jpaTestResultRepository) {
        this.jpaTestResultRepository = jpaTestResultRepository;
    }


    @Override
    public void saveTestResult(TestResult testResult) {
        jpaTestResultRepository.save(testResult);
    }
}
