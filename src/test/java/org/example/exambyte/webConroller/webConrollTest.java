package org.example.exambyte.webConroller;

import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class webConrollTest {

//    testing in still under working

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setupUserToLogin() {
    }


    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    void adminCanLoginInAdmin() throws Exception {
        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminTemp/adminDash"));
    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    void adminCanLoginInUser() throws Exception {
        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTemp/userDash"));
    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    void adminCanLoginInKorrektor() throws Exception {
        mvc.perform(get("/KorrektorDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("KorrektorTemp/korrektorDash"));
    }

    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void userCanLoginInUser() throws Exception {
        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTemp/userDash"));
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void userCantLoginInAdmin() throws Exception {
        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTemp/userDash"));
    }


}
































