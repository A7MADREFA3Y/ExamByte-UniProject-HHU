package org.example.exambyte.webConroller;

import org.example.exambyte.helper.WithMockOAuth2User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class webConrollTest {

//    testing in still under working

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("lading web page for main Controller")
    void testingLadingPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());


    }

    @Test
    @DisplayName("user loged in as ROLE_USER can go to userDashBoard")
    @WithMockOAuth2User(login = "JoeSchmoe")
    void loggedInUserInUserDash() throws Exception {
        mvc.perform(get("/userDashBoard/"))
            .andExpect(status().isOk());
    }


    @Test
    @DisplayName("user loged in as ROLE_USER can go to userDashBoard")
    void notLoggedInUserInUserDash() throws Exception {
        mvc.perform(get("/userDashBoard/"))
                .andExpect(status().is2xxSuccessful());
    }


//
//    @Test
//    @DisplayName("user loged in as ROLE_USER can go to userDashBoard")
////    @WithMockOAuth2User(login = "RudiRoot", roles = {"USER", "KORREKTOR"})
//    void userRoleCantAccsesAdminDash() throws Exception {
//        mvc.perform(get("/adminDashBoard/hey"))
//                .andExpect(status().isForbidden());
//    }


}
































