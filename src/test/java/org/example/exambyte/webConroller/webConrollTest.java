package org.example.exambyte.webConroller;

import org.example.exambyte.helper.WithMockOAuth2User;
import org.example.exambyte.service.ServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class webConrollTest {


    @Autowired
    MockMvc mvc;

     @MockBean
     private ServiceImp service;


    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    void adminCanLoginInAdmin() throws Exception {

        when(service.checkIfAdmin(any())).thenReturn(true);

        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminTemp/adminDash"));
    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    void adminCanLoginInUser() throws Exception {

        when(service.checkIfAdmin(any())).thenReturn(true);

        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTemp/userDash"));
    }

    @Test
    @WithMockOAuth2User(login = "admin" ,roles = "ADMIN")
    void adminCanLoginInKorrektor() throws Exception {

        when(service.checkIfKorrektor(any())).thenReturn(true);

        mvc.perform(get("/korrektorDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("KorrektorTemp/korrektorDash"));
    }

    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void userCanLoginInUser() throws Exception {

        when(service.checkIfUser(any())).thenReturn(true);

        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("UserTemp/userDash"));
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void userCantLoginInAdmin() throws Exception {

        when(service.checkIfAdmin(any())).thenReturn(false);

        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void userCantLoginInKorrektor() throws Exception {

        when(service.checkIfKorrektor(any())).thenReturn(false);

        mvc.perform(get("/korrektorDashBoard/"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void korrektorCanLoginInKorrektor() throws Exception {

        when(service.checkIfKorrektor(any())).thenReturn(true);

        mvc.perform(get("/korrektorDashBoard/"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockOAuth2User(login = "korrektor" ,roles = "KORREKTOR")
    void korrektorCantLoginInAdmin() throws Exception {

        when(service.checkIfAdmin(any())).thenReturn(false);

        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(login = "korrektor" ,roles = "KORREKTOR")
    void korrektorCantLoginInUser() throws Exception {

        when(service.checkIfUser(any())).thenReturn(false);

        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isForbidden());
    }


}
































