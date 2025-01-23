package org.example.exambyte.application.service;

import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class UserAuthServiceTest {

    @Autowired
    UserServiceInterface userService;


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void userRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfUser(auth)).isTrue();
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void userRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfAdmin(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void userRoleIn_checkIfCorrector() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfCorrector(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void adminRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfUser(auth)).isTrue();

    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void adminRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfUser(auth)).isTrue();

    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void adminRoleIn_checkIfCorrector() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfCorrector(auth)).isTrue();

    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void correctorRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfAdmin(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void correctorRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfUser(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void correctorRoleIn_checkIfCorrector() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(userService.checkIfCorrector(auth)).isTrue();
    }




}