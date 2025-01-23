package org.example.exambyte.application.service.serviceTest;

import jakarta.validation.Valid;
import org.example.exambyte.application.dto.*;
import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.Test;
import org.example.exambyte.domain.model.TestResult;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceInterface {

    void saveTest(TestsDto test);

    List<Test> getAllTests();

    void deleteTest(Long testId);

    Test findTestById(Long testId);

    String getGithubUsername();

    void updateTestFromDto(Long testId, @Valid TestsDto testsDto);

    void saveAnswer(AnswerDto answerDto);

    void updateAnswer(AnswerDto answerDto);

    boolean checkIfAllradySubmettBefore(String username, Test test);

    List<TestDtoDisplayOnly> getAllTestDtoDisplayOnly(List<Test> allTests);

    String getRemainingTime (LocalDateTime startTime, LocalDateTime endTime);

    double getTheMCQPoints(AnswersDto answersDto,List<Question> questions, Long testId);

    void saveTestResult(TestResultDto testResultDto);

    List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDto(Long testId, String username);

    List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(Long testId, String username);

    List<TestResult> getAllTestResultsWithTestIdAndUsername(Long testId, String githubUsername);

    List<Answer> getAllAnswersForFreeText(Long testId, String username);

    TestResult getTestResultWithTestIdAndUsername(Long testId, String username);

    void updateTestResult(TestResult testResultWithTestIdAndUsername);
}
