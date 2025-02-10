package org.example.exambyte.application.service.serviceTest;

import org.example.exambyte.application.dto.*;
import org.example.exambyte.domain.model.*;
import org.example.exambyte.domain.repository.AnswerRepository;
import org.example.exambyte.domain.repository.TestRepository;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.example.exambyte.infrasructure.repositoryImp.user.UserRepositoryImp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImp implements ServiceInterface {

    private final AnswerRepository answerRepository;

    private final TestResultRepository testResultRepository;



    public ServiceImp(AnswerRepository answerRepository,
                      TestRepository testRepository, TestResultRepository testResultRepository, UserRepositoryImp userRepositoryImp) {
        this.answerRepository = answerRepository;
        this.testResultRepository = testResultRepository;
    }

    /***
     *
     * @return the Logged-in username
     */

    public String getGithubUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String githubUsername = (String) oauth2User.getAttributes().get("login");
        if (githubUsername == null) {
            throw new IllegalStateException("GitHub username is not available");
        }
        return githubUsername;
    }


    /***
     *
     * @param allTests takes a List of the Test
     * @return a List of Test Dto Display Only
     * the idea of this methode was to make the data more flexible to use in frontend like getRemainingTime
     *
     */

    @Override
    public List<TestDtoDisplayOnly> getAllTestDtoDisplayOnly(List<Test> allTests) {

        List<TestDtoDisplayOnly> allTestDtoDisplayOnly = new ArrayList<>();
        for (Test test : allTests) {
//            boolean allradySubmett = checkIfAllradySubmettBefore(getGithubUsername(), test);

            TestDtoDisplayOnly testDtoDisplayOnly = TestDtoDisplayOnly.builder()
                    .id(test.getId())
                    .testName(test.getTestName())
                    .startTime(test.getStartTime())
                    .endTime(test.getEndTime())
                    .remainTime(getRemainingTime(test.getStartTime(), test.getEndTime()))
                    .expired(test.getEndTime().isBefore(LocalDateTime.now()))
//                    .submitted(allradySubmett)
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
    public double getTheMCQPoints(AnswersDto answersDto, List<Question> questions) {

        int mcqPoint = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCorrectAnswer() != null){
                if (questions.get(i).getCorrectAnswer().equals(answersDto.getAnswers().get(i).getAnswerText()))
                    mcqPoint++;
                }
            }
        return mcqPoint;

    }

}



















