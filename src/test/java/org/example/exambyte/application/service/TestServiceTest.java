package org.example.exambyte.application.service;

import org.example.exambyte.ExambyteApplication;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.domain.repository.TestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ExambyteApplication.class)
public class TestServiceTest {

    @InjectMocks
    private ServiceImp service; // Service being tested, injected with mocks

    @Mock
    private TestRepository testRepo; // Mocked repository


    @Test
    @DisplayName("getAllTests return a List with all test in Repository")
    void Methode_Return_getAllTests() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test(15L, "Mathe1");
        org.example.exambyte.domain.model.Test test2 = new org.example.exambyte.domain.model.Test(16L, "Mathe2");
        org.example.exambyte.domain.model.Test test3 = new org.example.exambyte.domain.model.Test(17L, "Mathe3");

        List<org.example.exambyte.domain.model.Test> testsFromRepo = Arrays.asList(test, test2, test3);

        when(testRepo.findAll()).thenReturn(testsFromRepo);

        List<org.example.exambyte.domain.model.Test> tests = service.getAllTests();

        assertThat(tests).isEqualTo(testsFromRepo);
    }


    @Test
    @DisplayName("deleteTest delete the Test from Repository")
    void Methode_Delete_DeleteTests() {
       Long id = 1L;

        service.deleteTest(id);

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

        org.example.exambyte.domain.model.Test testById = service.findTestById(5L);

        assertThat(test).isEqualTo(testById);
    }


    @Test
    @DisplayName("saveTest save the test to Repository clicks 2")
    void Methode_Save_SaveTestToRepository() {

        TestsDto testsDto = new TestsDto(15L , "Mathe1");
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test(15L, "Mathe1");

        service.saveTest(testsDto);

        testRepo.saveTest(test);

        verify(testRepo, times(1)).saveTest(test);


    }

}
