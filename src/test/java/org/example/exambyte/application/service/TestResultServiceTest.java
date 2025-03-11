package org.example.exambyte.application.service;

import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.application.service.testResultService.TestResultServiceImp;
import org.example.exambyte.domain.model.TestResult;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestResultServiceTest {

    @InjectMocks
    TestResultServiceImp testResultService;

    @Mock
    TestResultRepository testResultRepo;


    @Test
    @DisplayName("saveTestResult convert the Test Result from Dto to Entity to save")
    void testSaveTestResult() {
        TestResultDto testResultDto = TestResultDto.builder()
                .testId(13L)
                .takenBy("ahmad")
                .submitDate(LocalDateTime.now())
                .score(12.0)
                .passed(false)
                .graded(false)
                .build();

        testResultService.saveTestResult(testResultDto);

        TestResult testResult = new TestResult();

        testResultRepo.saveTestResult(testResult);

        verify(testResultRepo, times(1)).saveTestResult(testResult);

    }

    @Test
    @DisplayName("getAllTestResults using the TestId and the Username")
    void testGetAllTestResults() {

        Long testId = 1L;
        String username = "ahmad";
        List<TestResult> testResultsList = new ArrayList<>();

        TestResult testResult = TestResult.builder()
                .id(2L)
                .testId(testId)
                .takenBy(username)
                .submitDate(LocalDateTime.now())
                .grade(15.0)
                .passed(false)
                .graded(false)
                .build();

        testResultsList.add(testResult);

        when(testResultRepo.getAllTestResultsByTestIdAndUsername(testId, username)).thenReturn(testResultsList);

        List<TestResult> allTestResultsWithTestIdAndUsername = testResultService.getAllTestResultsWithTestIdAndUsername(testId, username);

        assertThat(allTestResultsWithTestIdAndUsername).isEqualTo(testResultsList);
    }

    
    @Test
    @DisplayName("getTestResultWithTestIdAndUsername the methode get only one specific TestResult")
    void testGetTestResultWithTestIdAndUsername() {
        Long testId = 1L;
        String username = "ahmad";


        TestResult testResult = TestResult.builder()
                .id(2L)
                .testId(testId)
                .takenBy(username)
                .submitDate(LocalDateTime.now())
                .grade(15.0)
                .graded(false)
                .passed(false)
                .build();

        when(testResultRepo.findTestResultByTestIdAndUsername(testId, username)).thenReturn(testResult);

        TestResult testResultWithTestIdAndUsername = testResultService.getTestResultWithTestIdAndUsername(testId, username);

        assertThat(testResultWithTestIdAndUsername).isEqualTo(testResult);

    }

    @Test
    @DisplayName("updateTestResult update the Test Result From the corrector")
    void testUpdateTestResult() {


        TestResultRepository mockTestResultRepo = Mockito.mock(TestResultRepository.class);

        TestResultServiceImp testResultServiceImp = Mockito.spy(new TestResultServiceImp(mockTestResultRepo));

        doReturn("refai").when(testResultServiceImp).getGithubUsername();

        TestResult testResult = TestResult.builder()
                .id(155L)
                .testId(123L)
                .takenBy("ahmad")
                .submitDate(LocalDateTime.now())
                .grade(15.0)
                .graded(false)
                .build();

        testResultServiceImp.updateTestResult(testResult);

        testResultRepo.saveTestResult(testResult);

        verify(testResultRepo,times(1)).saveTestResult(testResult);

    }

    @Test
    @DisplayName("updateTestResultWithNewAnswers mappe Entity to Dto")
    void testUpdateTestResultWithNewAnswers() {

        TestResult testResult = TestResult.builder()
                .id(155L)
                .testId(123L)
                .takenBy("ahmad")
                .submitDate(LocalDateTime.now())
                .grade(17.0)
                .graded(false)
                .graded(false)
                .build();

        when(testResultRepo.findTestResultByTestIdAndUsername(any(), any())).thenReturn(testResult);

        TestResultDto testResultDto = testResultService.updateTestResultWithNewAnswers(155L, "ahmad");

        assertThat(testResultDto).isNotNull();
        assertThat(testResultDto.getId()).isEqualTo(testResult.getId());
        assertThat(testResultDto.getTakenBy()).isEqualTo(testResult.getTakenBy());
    }

    @Test
    @DisplayName("updateTestResultDto takes Dto, update it and mapp it to Entity")
    public void testUpdateTestResultDtoTakesDto() {

    }











}