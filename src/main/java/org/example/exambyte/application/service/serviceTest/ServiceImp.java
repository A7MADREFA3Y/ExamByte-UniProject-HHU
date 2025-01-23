package org.example.exambyte.application.service.serviceTest;

import jakarta.transaction.Transactional;
import org.example.exambyte.application.dto.*;
import org.example.exambyte.domain.model.*;
import org.example.exambyte.domain.repository.AnswerRepository;
import org.example.exambyte.domain.repository.TestRepository;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.example.exambyte.infrasructure.repositoryImp.question.QuestionRepositoryImp;
import org.example.exambyte.infrasructure.repositoryImp.user.UserRepositoryImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImp implements ServiceInterface {


    private final AnswerRepository answerRepository;


    private final TestRepository testRepository;


    private final TestResultRepository testResultRepository;


    private QuestionRepositoryImp questionRepositoryImp;


    public ServiceImp(AnswerRepository answerRepository,
                      TestRepository testRepository, TestResultRepository testResultRepository, UserRepositoryImp userRepositoryImp) {
        this.answerRepository = answerRepository;
        this.testRepository = testRepository;
        this.testResultRepository = testResultRepository;
    }



    public String getGithubUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String githubUsername = (String) oauth2User.getAttributes().get("login");
        if (githubUsername == null) {
            throw new IllegalStateException("GitHub username is not available");
        }
        return githubUsername;
    }




    @Override
    public List<TestDtoDisplayOnly> getAllTestDtoDisplayOnly(List<Test> allTests) {

        List<TestDtoDisplayOnly> allTestDtoDisplayOnly = new ArrayList<>();
        for (Test test : allTests) {
            boolean allradySubmett = checkIfAllradySubmettBefore(getGithubUsername(), test);

            TestDtoDisplayOnly testDtoDisplayOnly = TestDtoDisplayOnly.builder()
                    .id(test.getId())
                    .testName(test.getTestName())
                    .startTime(test.getStartTime())
                    .endTime(test.getEndTime())
                    .remainTime(getRemainingTime(test.getStartTime(), test.getEndTime()))
                    .expired(test.getEndTime().isBefore(LocalDateTime.now()))
                    .submitted(allradySubmett)
                    .graded(checkIfTestIsGraded(test.getId(), getGithubUsername()))
                    .build();

            allTestDtoDisplayOnly.add(testDtoDisplayOnly);
        }
        return allTestDtoDisplayOnly;
    }

    @Override
    public boolean checkIfAllradySubmettBefore(String username, Test test) {
        List<Answer> allAnswersByUsername = answerRepository.getAllAnswersByUsername(username);
            for (Answer answer : allAnswersByUsername) {
                if (answer.getTestId().equals(test.getId())) {
                    return true;
            }
        }
        return false;
    }

    private boolean checkIfTestIsGraded(Long id, String githubUsername) {
        TestResult testResultByTestIdAndUsername = testResultRepository.findTestResultByTestIdAndUsername(id, githubUsername);

        if (testResultByTestIdAndUsername == null) {
            return false;
        }

        return testResultByTestIdAndUsername.getGraded();
    }


    @Override
    public String getRemainingTime(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(endTime)) {
            return "Test has ended";
        }

        Duration duration = Duration.between(now, endTime);


        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return String.format("%d days, %02d hours, %02d minutes", days, hours, minutes);
    }

    @Override
    public double getTheMCQPoints(AnswersDto answersDto, List<Question> questions, Long testId) {

        int mcqPoint = 0;
        for (Question question : questions) {
            boolean checkIfQuestionAllradychecked = false;
            for (AnswerDto answer : answersDto.getAnswers()) {
                if (question.getQuestionType() == QuestionType.MCQ) {
                    if (question.getCorrectAnswer().equals(answer.getAnswerText()) && !checkIfQuestionAllradychecked) {
                        mcqPoint++;
                        checkIfQuestionAllradychecked = true;
                    }
                }
            }
        }

        return mcqPoint;

    }

//
//    private Answer mapToAnswer(AnswerDto answerDto) {
//        return Answer.builder()
//                .testId(answerDto.getTestId())
//                .questionId(answerDto.getQuestionId())
//                .answerText(answerDto.getAnswerText())
//                .takenBy(answerDto.getTakenBy())
//                .correctedAnswer(answerDto.getCorrectedAnswer())
//                .build();
//    }
//
//
//    private Test mapToTest(TestsDto testsDto) {
//
//        return Test.builder()
//                .id(testsDto.getId())
//                .testName(testsDto.getTestName())
//                .createdBy(testsDto.getCreatedBy())
//                .startTime(testsDto.getStartTime())
//                .endTime(testsDto.getEndTime())
//                .resultPublicationTime(testsDto.getResultPublicationTime())
//                .build();
//
//    }
//
//
//    private Test mapToTest(Test test) {
//
//        return Test.builder()
//                .id(test.getId())
//                .testName(test.getTestName())
//                .createdBy(test.getCreatedBy())
//                .startTime(test.getStartTime())
//                .endTime(test.getEndTime())
//                .resultPublicationTime(test.getResultPublicationTime())
//                .build();
//
//    }
//

}



















