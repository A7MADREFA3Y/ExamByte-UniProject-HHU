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

    String getGithubUsername();

    boolean checkIfAllradySubmettBefore(String username, Test test);

    List<TestDtoDisplayOnly> getAllTestDtoDisplayOnly(List<Test> allTests);

    String getRemainingTime (LocalDateTime startTime, LocalDateTime endTime);

    double getTheMCQPoints(AnswersDto answersDto,List<Question> questions, Long testId);

}
