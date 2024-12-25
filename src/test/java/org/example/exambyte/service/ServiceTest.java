package org.example.exambyte.service;

import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class ServiceTest {

    @Autowired
    ServiceImp service;

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
    public void userRoleIn_CheckIfKorrektor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfKorrektor(auth)).isFalse();
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
    public void adminRoleIn_CheckIfKorrektor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfKorrektor(auth)).isTrue();

    }


    @Test
    @WithMockOAuth2User(login = "korrektor" ,roles = "KORREKTOR")
    public void korrektorRoleIn_CheckIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfAdmin(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "korrektor" ,roles = "KORREKTOR")
    public void korrektorRoleIn_CheckIfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfUser(auth)).isFalse();
    }


    @Test
    @WithMockOAuth2User(login = "korrektor" ,roles = "KORREKTOR")
    public void korrektorRoleIn_CheckIfkorrektor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(service.checkIfKorrektor(auth)).isTrue();
    }


}
