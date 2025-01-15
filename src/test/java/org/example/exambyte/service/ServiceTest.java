package org.example.exambyte.service;

import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.domain.repository.TestRepository;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ServiceTest {

    @Autowired
    ServiceImp service;

    @MockBean
    TestRepository testsRepository;


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void userRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfUser(auth)).isTrue();
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void userRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfAdmin(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void userRoleIn_checkIfCorrector() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfCorrector(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void adminRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfUser(auth)).isTrue();

    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void adminRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfUser(auth)).isTrue();

    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void adminRoleIn_checkIfCorrector() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfCorrector(auth)).isTrue();

    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void correctorRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfAdmin(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void correctorRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfUser(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void correctorRoleIn_checkIfCorrector() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfCorrector(auth)).isTrue();
    }

    @Test
    void Methode_Return_getAllTests() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test(15L, "Mathe1");
        org.example.exambyte.domain.model.Test test2 = new org.example.exambyte.domain.model.Test(16L, "Mathe2");
        org.example.exambyte.domain.model.Test test3 = new org.example.exambyte.domain.model.Test(17L, "Mathe3");

        when(testsRepository.findAll()).thenReturn(List.of(test, test2, test3));

        List<org.example.exambyte.domain.model.Test> tests = service.getAllTests();

        assertThat(tests.size()).isEqualTo(3);
        assertThat(tests.get(0)).isEqualTo(test);
        assertThat(tests.get(1)).isEqualTo(test2);
        assertThat(tests.get(2)).isEqualTo(test3);
    }


    @Test
    void Methode_Delete_DeleteTests() {
        Long testId = 1L;
        service.deleteTest(testId);
        verify(testsRepository, times(1)).deleteById(testId);
    }


    @Test
    void Methode_Find_FindTestById() {
        Long testId = 5L;
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test(testId, "Mathe1");
        when(testsRepository.findById(testId)).thenReturn(test);

        org.example.exambyte.domain.model.Test result = service.findTestById(testId);

        assertThat(result.getId()).isEqualTo(testId);
        assertThat(result.getTestName()).isEqualTo("Mathe1");
    }

//
//
//    @Test
//    void Methode_save_saveTest() {
//       TestsDto dto = new TestsDto(13L, "Propra 1");
//       org.example.exambyte.model.Test test = new org.example.exambyte.model.Test(13L, "Propra 1");
//
//        service.saveTest(dto);
//
//        verify(testsRepository, times(1)).save(test);
//        assertThat(dto.getId()).isEqualTo(test.getId());
//        assertThat(dto.getTestName()).isEqualTo(test.getTestName());
//
//    }

















    private org.example.exambyte.domain.model.Test mapToTest(TestsDto testsDto) {

        return org.example.exambyte.domain.model.Test.builder()
                .id(testsDto.getId())
                .testName(testsDto.getTestName())
                .createdBy(testsDto.getCreatedBy())
                .startTime(testsDto.getStartTime())
                .endTime(testsDto.getEndTime())
                .resultPublicationTime(testsDto.getResultPublicationTime())
                .build();

    }


    private TestsDto mapToTestDto(org.example.exambyte.domain.model.Test test) {

        return TestsDto.builder()
                .id(test.getId())
                .testName(test.getTestName())
                .createdBy(test.getCreatedBy())
                .startTime(test.getStartTime())
                .endTime(test.getEndTime())
                .resultPublicationTime(test.getResultPublicationTime())
                .build();

    }

}