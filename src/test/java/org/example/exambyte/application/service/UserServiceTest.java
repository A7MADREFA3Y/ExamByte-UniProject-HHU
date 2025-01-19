package org.example.exambyte.application.service;

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
public class UserServiceTest {

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

}