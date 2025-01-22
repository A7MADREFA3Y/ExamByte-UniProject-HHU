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

@Service
public class ServiceImp implements ServiceInterface {


    private final AnswerRepository answerRepository;


    private final TestRepository testRepository;


    private final TestResultRepository testResultRepository;
    private final UserRepositoryImp userRepositoryImp;


    private QuestionRepositoryImp questionRepositoryImp;


    public ServiceImp(AnswerRepository answerRepository,
                      TestRepository testRepository, TestResultRepository testResultRepository, UserRepositoryImp userRepositoryImp) {
        this.answerRepository = answerRepository;
        this.testRepository = testRepository;
        this.testResultRepository = testResultRepository;
//        this.userRepository = userRepository;
        this.userRepositoryImp = userRepositoryImp;
    }


    @Override
    public boolean checkIfAdmin(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isAdmin;
    }

    @Override
    public boolean checkIfCorrector(Authentication auth) {
        boolean isCorrector = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_CORRECTOR") ||
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isCorrector;
    }

    @Override
    public boolean checkIfUser(Authentication auth) {

        boolean isUser = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_USER") ||
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isUser;
    }



    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);

    }

//    it works but no but still without testing
    @Override
    public Test findTestById(Long testId) {
    return testRepository.findById(testId);
    }

    @Override
    public void saveTest(TestsDto testsDto) {
        Test test = mapToTest(testsDto);
        testRepository.saveTest(test);
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
    @Transactional
    public void updateTestFromDto(Long testId, TestsDto testsDto) {
        Test test = testRepository.findById(testId);

        test.setTestName(testsDto.getTestName());
        test.setStartTime(testsDto.getStartTime());
        test.setEndTime(testsDto.getEndTime());
        test.setResultPublicationTime(testsDto.getResultPublicationTime());

        testRepository.saveTest(test);
    }

    @Override
    public void saveAnswer(AnswerDto answerDto) {
        Answer answer = mapToAnswer(answerDto);
        answerRepository.saveAnswer(answer);
    }

    @Override
    public void updateAnswer(AnswerDto answerDto) {
        Answer answerByTestIdAndQuestion = answerRepository.findAnswerByTestIdAndQuestion(answerDto.getTestId(), answerDto.getQuestionId());

        answerByTestIdAndQuestion.setId(answerByTestIdAndQuestion.getId());
        answerByTestIdAndQuestion.setQuestionId(answerDto.getQuestionId());
        answerByTestIdAndQuestion.setTestId(answerDto.getTestId());
        answerByTestIdAndQuestion.setAnswerText(answerDto.getAnswerText());
        answerByTestIdAndQuestion.setTakenBy(answerDto.getTakenBy());
        answerByTestIdAndQuestion.setCorrectedAnswer(answerDto.getCorrectedAnswer());

        answerRepository.saveAnswer(answerByTestIdAndQuestion);
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

    @Override
    public List<TestDtoDisplayOnly> getallTestDtoDisplayOnly(List<Test> allTests) {

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
                    .build();

            allTestDtoDisplayOnly.add(testDtoDisplayOnly);
        }
        return allTestDtoDisplayOnly;
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

    @Override
    public void saveTestResult(TestResultDto testResultDto) {
        TestResult testResult = TestResult.builder()
                .testId(testResultDto.getTestId())
                .takenBy(testResultDto.getTakenBy())
                .submitDate(testResultDto.getSubmitDate())
                .grade(testResultDto.getScore())
                .passed(testResultDto.getPassed())
                .graded(testResultDto.getGraded())
                .build();



        testResultRepository.saveTestResult(testResult);

    }

    @Override
    public List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDto(Long testId, String username) {
        List<Answer> allAnswersByTestIdAndUsername = answerRepository.getAllAnswersByTestIdAndUsername(testId, username);
        return mapAnswerToAnswerDto(allAnswersByTestIdAndUsername);
    }

    @Override
    public List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(Long testId, String username) {
        List<Answer> allAnswersByTestIdAndUsername = answerRepository.findAllAnswersForFreeText(testId, username);
        return mapAnswerToAnswerDto(allAnswersByTestIdAndUsername);
    }

    private List<AnswerDto> mapAnswerToAnswerDto(List<Answer> answers) {
        List<AnswerDto> answerDtos = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerDto answerDto = mapAnswerToDto(answer);
            answerDtos.add(answerDto);
        }
        return answerDtos;
     }

    private AnswerDto mapAnswerToDto(Answer answer) {
        return AnswerDto.builder()
                .testId(answer.getTestId())
                .questionId(answer.getQuestionId())
                .answerText(answer.getAnswerText())
                .takenBy(answer.getTakenBy())
                .correctedAnswer(answer.getCorrectedAnswer())
                .build();
    }

    @Override
    public List<TestResult> getAllTestResultsWithTestIdAndUsername(Long testId, String username) {
        return testResultRepository.getAllTestResultsByTestIdAndUsername(testId, username);
    }

    @Override
    public List<Answer> getAllAnswersForFreeText(Long testId, String username) {
        return answerRepository.findAllAnswersForFreeText(testId, username);
    }

    @Override
    public TestResult getTestResultWithTestIdAndUsername(Long testId, String username) {
        return testResultRepository.findTestResultByTestIdAndUsername(testId, username);
    }

    @Override
    public void updateTestResult(TestResult testResultWithTestIdAndUsername) {
        testResultWithTestIdAndUsername.setId(testResultWithTestIdAndUsername.getId());
        testResultWithTestIdAndUsername.setTestId(testResultWithTestIdAndUsername.getTestId());
        testResultWithTestIdAndUsername.setTakenBy(testResultWithTestIdAndUsername.getTakenBy());
        testResultWithTestIdAndUsername.setSubmitDate(testResultWithTestIdAndUsername.getSubmitDate());
        testResultWithTestIdAndUsername.setGrade(testResultWithTestIdAndUsername.getGrade());
        testResultWithTestIdAndUsername.setPassed(testResultWithTestIdAndUsername.getPassed());
        testResultWithTestIdAndUsername.setGraded(true);
        testResultWithTestIdAndUsername.setCorrectedBy(getGithubUsername());

        testResultRepository.saveTestResult(testResultWithTestIdAndUsername);
    }


    private Answer mapToAnswer(AnswerDto answerDto) {
        return Answer.builder()
                .testId(answerDto.getTestId())
                .questionId(answerDto.getQuestionId())
                .answerText(answerDto.getAnswerText())
                .takenBy(answerDto.getTakenBy())
                .correctedAnswer(answerDto.getCorrectedAnswer())
                .build();
    }


    private Test mapToTest(TestsDto testsDto) {

        return Test.builder()
                .id(testsDto.getId())
                .testName(testsDto.getTestName())
                .createdBy(testsDto.getCreatedBy())
                .startTime(testsDto.getStartTime())
                .endTime(testsDto.getEndTime())
                .resultPublicationTime(testsDto.getResultPublicationTime())
                .build();

    }


    private Test mapToTest(Test test) {

        return Test.builder()
                .id(test.getId())
                .testName(test.getTestName())
                .createdBy(test.getCreatedBy())
                .startTime(test.getStartTime())
                .endTime(test.getEndTime())
                .resultPublicationTime(test.getResultPublicationTime())
                .build();

    }


}



















