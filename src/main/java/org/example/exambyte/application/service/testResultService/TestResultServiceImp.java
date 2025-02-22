package org.example.exambyte.application.service.testResultService;

import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.domain.model.TestResult;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultServiceImp implements TestResultServiceInterface{

    private final TestResultRepository testResultRepository;

    public TestResultServiceImp(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
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
    public List<TestResult> getAllTestResultsWithTestIdAndUsername(Long testId, String username) {
        return testResultRepository.getAllTestResultsByTestIdAndUsername(testId, username);
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

    @Override
    public TestResultDto updateTestResultWithNewAnswers(Long testId, String username) {
        TestResult testResultByTestIdAndUsername = testResultRepository.findTestResultByTestIdAndUsername(testId, username);

        return TestResultDto.builder()
                .id(testResultByTestIdAndUsername.getId())
                .testId(testResultByTestIdAndUsername.getTestId())
                .takenBy(testResultByTestIdAndUsername.getTakenBy())
                .submitDate(testResultByTestIdAndUsername.getSubmitDate())
                .score(testResultByTestIdAndUsername.getGrade())
                .passed(testResultByTestIdAndUsername.getPassed())
                .graded(testResultByTestIdAndUsername.getGraded())
                .build();
    }

    @Override
    public void updateTestResultDto(TestResultDto testResultDtoUpdate) {
        Long testId = testResultDtoUpdate.getTestId();
        String takenBy = testResultDtoUpdate.getTakenBy();
        TestResult testResultByTestIdAndUsername = testResultRepository.findTestResultByTestIdAndUsername(testId, takenBy);
        testResultByTestIdAndUsername.setSubmitDate(testResultDtoUpdate.getSubmitDate());
        testResultByTestIdAndUsername.setGrade(testResultDtoUpdate.getScore());
        testResultRepository.saveTestResult(testResultByTestIdAndUsername);
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

}
