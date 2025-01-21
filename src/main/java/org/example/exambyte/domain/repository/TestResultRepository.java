package org.example.exambyte.domain.repository;

import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.domain.model.TestResult;

import java.util.List;

public interface TestResultRepository {

    List<TestResult> getAllTestResultsByTestIdAndUsername(Long testId, String username);

    void saveTestResult(TestResult testResult);

    TestResult findTestResultByTestIdAndUsername(Long testId, String username);
}
