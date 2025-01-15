package org.example.exambyte.webConroller;

import org.example.exambyte.helper.WithMockOAuth2User;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    void adminCanLoginInCorrector() throws Exception {

        when(service.checkIfCorrector(any())).thenReturn(true);

        mvc.perform(get("/correctorDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("CorrectorTemp/correctorDash"));
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

        when(service.checkIfCorrector(any())).thenReturn(false);

        mvc.perform(get("/correctorDashBoard/"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockOAuth2User(login = "user" ,roles = "USER")
    void correctorCanLoginInCorrector() throws Exception {

        when(service.checkIfCorrector(any())).thenReturn(true);

        mvc.perform(get("/correctorDashBoard/"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    void correctorCantLoginInAdmin() throws Exception {

        when(service.checkIfAdmin(any())).thenReturn(false);

        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2User(login = "corrector" ,roles = "CORRECTOR")
    void correctorCantLoginInUser() throws Exception {

        when(service.checkIfUser(any())).thenReturn(false);

        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().isForbidden());
    }
}
