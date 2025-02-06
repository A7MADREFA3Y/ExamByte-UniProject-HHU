package org.example.exambyte.application.service;


import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserServiceInterface userService;




    @Test
    @DisplayName("checkIfAdmin and he is Admin")
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void testCheckIfAdminAndRoleAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(userService.checkIfAdmin(auth)).isEqualTo(true);

    }

    @Test
    @DisplayName("checkIfAdmin But he is USER")
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void testCheckIfAdminAndRoleUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(userService.checkIfAdmin(auth)).isEqualTo(false);

    }


    @Test
    @DisplayName("checkIfUser and he is USER")
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    public void testCheckIfUserAndRoleUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(userService.checkIfUser(auth)).isEqualTo(true);

    }

    @Test
    @DisplayName("checkIfUser and he is Admin")
    @WithMockOAuth2User(login = "user" ,roles = "ADMIN")
    public void testCheckIfUserAndRoleAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(userService.checkIfUser(auth)).isEqualTo(true);

    }


    @Test
    @DisplayName("checkIfCorrector and he is corrector")
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    public void testCheckIfCorrectorAndRoleCorrector() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(userService.checkIfCorrector(auth)).isEqualTo(true);

    }

    @Test
    @DisplayName("checkIfCorrector and he is admin")
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    public void testCheckIfCorrectorAndRoleAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        assertThat(userService.checkIfCorrector(auth)).isEqualTo(true);

    }



    @Test
    @DisplayName("getGithubUsername return the name of the logged in user")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    public void getGithubUsernameReturnTheNameOfTheLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String githubUsername = (String) oauth2User.getAttributes().get("login");

        String username = userService.getGithubUsername();

        assertThat(username).isEqualTo(githubUsername);

    }
}
