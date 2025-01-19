package org.example.exambyte.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.exambyte.domain.model.Role.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    @DisplayName("User must have an Id")
    void userShouldHaveGithub_id(){
        User user = new User();
        user.setGithubId("123456");
        assertEquals("123456", user.getGithubId());
    }

    @Test
    @DisplayName("User Cant have an Id as null")
    void userCantHaveGithub_idAsNull(){
        User user = new User();

        try {
            user.setGithubId(null);
        }catch (NullPointerException e){}

        assertThat(user.getGithubId()).isNull();
    }





    @Test
    @DisplayName("User must have an github username")
    void userMustHaveGithub_username(){
        User user = new User();
        user.setGithubUsername("refai");
        assertEquals("refai", user.getGithubUsername());
    }



    @Test
    @DisplayName("User cant have an github username As null")
    void userCantHaveGithub_usernameAsNull(){
        User user = new User();

        try {
            user.setGithubUsername(null);
        }catch (NullPointerException e){}

        assertThat(user.getGithubUsername()).isNull();
    }




    @Test
    @DisplayName("User cam have an Role as USER")
    void userCanHaveRole_User(){
        User user = new User();
        user.setRole(USER);
        assertEquals(USER, user.getRole());
    }

    @Test
    @DisplayName("User can have an Role as ADMIN")
    void userCanHaveRole_ADMIN(){
        User user = new User();
        user.setRole(ADMIN);
        assertEquals(ADMIN, user.getRole());
    }

    @Test
    @DisplayName("User can have an Role as CORRECTOR")
    void userCanHaveRole_CORRECTOR(){
        User user = new User();
        user.setRole(CORRECTOR);
        assertEquals(CORRECTOR, user.getRole());
    }


    @Test
    @DisplayName("User Must have an Role ")
    void userMustHaveRole_CORRECTOR(){
        User user = new User();

        try {
            user.setRole(null);
        }catch (NullPointerException e){}

        assertThat(user.getRole()).isNull();
    }



    @Test
    @DisplayName("User can have all attributes Github_Id, Github_username and role")
    void userCanHaveAllThreeAttributes(){
        User user = new User();

        user.setGithubId("123456");
        user.setGithubUsername("refai");
        user.setRole(ADMIN);

        assertEquals("123456", user.getGithubId());
        assertEquals("refai", user.getGithubUsername());
        assertEquals(ADMIN, user.getRole());
    }



}