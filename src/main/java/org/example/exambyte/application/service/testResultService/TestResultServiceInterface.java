package org.example.exambyte.application.service.testResultService;

import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.domain.model.TestResult;

import java.util.List;

public interface TestResultServiceInterface {

    String getGithubUsername();

    void saveTestResult(TestResultDto testResultDto);

    List<TestResult> getAllTestResultsWithTestIdAndUsername(Long testId, String githubUsername);

    TestResult getTestResultWithTestIdAndUsername(Long testId, String username);

    void updateTestResult(TestResult testResultWithTestIdAndUsername);

    TestResultDto updateTestResultWithNewAnswers(Long testId, String username);

    void updateTestResultDto(TestResultDto testResultDtoupdate);
}
