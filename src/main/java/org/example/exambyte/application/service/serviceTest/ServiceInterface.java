package org.example.exambyte.application.service.serviceTest;

import jakarta.validation.Valid;
import org.example.exambyte.application.dto.*;
import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.Test;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceInterface {

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfCorrector(Authentication auth);

    boolean checkIfUser(Authentication auth);

    void saveTest(TestsDto test);

    List<Test> getAllTests();

    void deleteTest(Long testId);

    Test findTestById(Long testId);

    String getGithubUsername();

    void updateTest(@Valid TestsDto testDto);

    void updateTestFromDto(Long testId, @Valid TestsDto testsDto);

    void saveAnswer(AnswerDto answerDto);

    boolean checkIfAllradySubmettBefore(String username, Test test);

    List<TestDtoDisplayOnly> getallTestDtoDisplayOnly(List<Test> allTests);

    String getRemainingTime (LocalDateTime startTime, LocalDateTime endTime);

    double getTheMCQPoints(AnswersDto answersDto,List<Question> questions, Long testId);

    void saveTestResult(TestResultDto testResultDto);
}
