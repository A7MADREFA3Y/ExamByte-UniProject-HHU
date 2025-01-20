package org.example.exambyte.application.service;


import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.domain.model.Role;
import org.example.exambyte.domain.model.User;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    ServiceImp service;




    @Test
    @DisplayName("checkIfAdmin and he is Admin")
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void testCheckIfAdminAndRoleAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(service.checkIfAdmin(auth)).isEqualTo(true);

    }

    @Test
    @DisplayName("checkIfAdmin But he is USER")
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void testCheckIfAdminAndRoleUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(service.checkIfAdmin(auth)).isEqualTo(false);

    }


    @Test
    @DisplayName("checkIfUser and he is USER")
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void testCheckIfUserAndRoleUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(service.checkIfUser(auth)).isEqualTo(true);

    }

    @Test
    @DisplayName("checkIfUser and he is Admin")
    @WithMockOAuth2User(login = "user" ,roles = "ADMIN")
    public void testCheckIfUserAndRoleAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(service.checkIfUser(auth)).isEqualTo(true);

    }


    @Test
    @DisplayName("checkIfCorrector and he is corrector")
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void testCheckIfCorrectorAndRolecorrector() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(service.checkIfCorrector(auth)).isEqualTo(true);

    }

    @Test
    @DisplayName("checkIfCorrector and he is admin")
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void testCheckIfCorrectorAndRoleAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(service.checkIfCorrector(auth)).isEqualTo(true);

    }






















}
