package org.example.exambyte.infrasructure.repositoryImp.testResult;

import org.example.exambyte.domain.model.TestResult;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestResultRepositoryImp implements TestResultRepository {
    private final JpaTestResultRepository jpaTestResultRepository;

    public TestResultRepositoryImp(JpaTestResultRepository jpaTestResultRepository) {
        this.jpaTestResultRepository = jpaTestResultRepository;
    }


    @Override
    public List<TestResult> getAllTestResultsByTestIdAndUsername(Long testId, String username) {
        return jpaTestResultRepository.findAllByTestIdAndUsername(testId, username);
    }

    @Override
    public void saveTestResult(TestResult testResult) {
        jpaTestResultRepository.save(testResult);
    }

    @Override
    public TestResult findTestResultByTestIdAndUsername(Long testId, String username) {
        return jpaTestResultRepository.findTestResultByTestIdAndTakenBy(testId, username);
    }
}
