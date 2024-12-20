package org.example.exambyte.webConroller;

import org.example.exambyte.webSecurityConfig.MethodSecurityConfig;
import org.example.exambyte.webSecurityConfig.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class webconrollTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("testing the lading web page for Admin Controller")
//    @WithMockOAuth2User(login = "A7MADREFA3Y", roles = {"USER", "ADMIN"})
    void testingLadingPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());


    }

}
































