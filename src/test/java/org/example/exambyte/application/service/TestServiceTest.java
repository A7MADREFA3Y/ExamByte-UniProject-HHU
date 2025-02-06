package org.example.exambyte.application.service;

import org.example.exambyte.ExambyteApplication;
import org.example.exambyte.application.dto.TestDtoDisplayOnly;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.application.service.testService.TestServiceImp;
import org.example.exambyte.domain.repository.AnswerRepository;
import org.example.exambyte.domain.repository.TestRepository;
import org.example.exambyte.domain.repository.TestResultRepository;
import org.example.exambyte.infrasructure.repositoryImp.user.UserRepositoryImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ExambyteApplication.class)
public class TestServiceTest {

    @InjectMocks
    private TestServiceImp testService; // Service being tested, injected with mocks

    @Mock
    private TestRepository testRepo; // Mocked repository


    @Test
    @DisplayName("saveTest save the test to Repository clicks 2")
    void Methode_Save_SaveTestToRepository() {

        TestsDto testsDto = new TestsDto(15L , "Mathe1");
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test(15L, "Mathe1");

        testService.saveTest(testsDto);

        testRepo.saveTest(test);

        verify(testRepo, times(1)).saveTest(test);

    }

    @Test
    @DisplayName("getAllTests return a List with all test in Repository")
    void Methode_Return_getAllTests() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test(15L, "Mathe1");
        org.example.exambyte.domain.model.Test test2 = new org.example.exambyte.domain.model.Test(16L, "Mathe2");
        org.example.exambyte.domain.model.Test test3 = new org.example.exambyte.domain.model.Test(17L, "Mathe3");

        List<org.example.exambyte.domain.model.Test> testsFromRepo = Arrays.asList(test, test2, test3);

        when(testRepo.findAll()).thenReturn(testsFromRepo);

        List<org.example.exambyte.domain.model.Test> tests = testService.getAllTests();

        assertThat(tests).isEqualTo(testsFromRepo);
    }


    @Test
    @DisplayName("deleteTest delete the Test from Repository")
    void Methode_Delete_DeleteTests() {
       Long id = 1L;

        testService.deleteTest(id);

       testRepo.deleteById(id);

       verify(testRepo, times(2)).deleteById(id);

    }


    @Test
    @DisplayName("findTestById finds the test using an Id")
    void Methode_Find_FindTestById() {


        org.example.exambyte.domain.model.Test test = org.example.exambyte.domain.model.Test.builder()
                .id(5L)
                .build();

        when(testRepo.findById(5L)).thenReturn(test);

        org.example.exambyte.domain.model.Test testById = testService.findTestById(5L);

        assertThat(test).isEqualTo(testById);
    }



    @Test
    @DisplayName("updateTestFromDto this methode takes the old info from test and update it")
    public void Methode_Update_UpdateTestFromDto() {

        Long testId = 1L;
        TestsDto testsDto = new TestsDto();
        testsDto.setId(testId);
        testsDto.setTestName("Mathe1");
        testsDto.setStartTime(LocalDateTime.now());
        testsDto.setEndTime(LocalDateTime.now().plusDays(5));
        testsDto.setResultPublicationTime(LocalDateTime.now().plusDays(6));

        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        when(testRepo.findById(testId)).thenReturn(test);

        testService.updateTestFromDto(testId, testsDto);

        verify(testRepo, times(1)).saveTest(test);

    }


    @Test
    @DisplayName("getAllTestDtoDisplayOnly reformats Entity to Dto for flexible use")
    public void methode_getAllTestDtoDisplayOnly() {
        // Mock repositories
        AnswerRepository answerRepository = Mockito.mock(AnswerRepository.class);
        TestRepository testRepository = Mockito.mock(TestRepository.class);
        TestResultRepository testResultRepository = Mockito.mock(TestResultRepository.class);
        UserRepositoryImp userRepositoryImp = Mockito.mock(UserRepositoryImp.class);

        // Create a spy for ServiceImp
        ServiceImp serviceImpSpy = Mockito.spy(new ServiceImp(answerRepository, testRepository, testResultRepository, userRepositoryImp));

        // Stub getGithubUsername
        doReturn("MockedGithubUser").when(serviceImpSpy).getGithubUsername();

        // Test data setup
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(5);

        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(15L);
        test.setTestName("Mathe1");
        test.setStartTime(startTime);
        test.setEndTime(endTime);
        test.setResultPublicationTime(LocalDateTime.now().plusDays(6));

        List<org.example.exambyte.domain.model.Test> testsFromRepo = List.of(test);

        // Stub checkIfAllradySubmettBefore
        doReturn(false).when(serviceImpSpy).checkIfAllradySubmettBefore("MockedGithubUser", test);

        // Manually create the expected TestDtoDisplayOnly
        TestDtoDisplayOnly testDtoDisplayOnly = TestDtoDisplayOnly.builder()
                .id(test.getId())
                .testName(test.getTestName())
                .startTime(test.getStartTime())
                .endTime(test.getEndTime())
                .remainTime(serviceImpSpy.getRemainingTime(test.getStartTime(), test.getEndTime()))
                .expired(test.getEndTime().isBefore(LocalDateTime.now()))
                .submitted(false)
                .build();

        List<TestDtoDisplayOnly> ListFromTestLayer = List.of(testDtoDisplayOnly);

        // Call the service method
        List<TestDtoDisplayOnly> ListFromServiceLayer = serviceImpSpy.getAllTestDtoDisplayOnly(testsFromRepo);

        // Assert the results
        assertThat(ListFromServiceLayer).isEqualTo(ListFromTestLayer);
    }













}
