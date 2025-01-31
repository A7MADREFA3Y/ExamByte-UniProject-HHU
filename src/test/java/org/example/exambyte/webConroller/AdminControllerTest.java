package org.example.exambyte.webConroller;

import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.application.service.testService.TestServiceInterface;
import org.example.exambyte.application.service.userService.UserServiceImp;
import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.example.exambyte.presentaion.webConroller.AdminController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {


    @Autowired
    MockMvc mvc;

    @MockitoBean
    UserServiceInterface userService;

    @MockitoBean
    ServiceInterface service;
    @MockitoBean
    TestServiceInterface testService;
    @MockitoBean
    ServiceQuestionInterface questionService;


    @Test
    @DisplayName("Testing the root of /adminDashBoard")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testRootDashBoard() throws Exception {

        List<org.example.exambyte.domain.model.Test> tests = new ArrayList<>();

        when(userService.checkIfAdmin(any())).thenReturn(true);
        when(service.getGithubUsername()).thenReturn("username");
        when(testService.getAllTests()).thenReturn(tests);


        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminTemp/adminDash"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("tests"));
    }

    @Test
    @DisplayName("Testing the root of /adminDashBoard to return Page SC_FORBIDDEN")
    @WithMockOAuth2User(login = "user", roles = "USER")
    void testRootDashBoardForbidden() throws Exception {

        when(userService.checkIfAdmin(any())).thenReturn(false);

        mvc.perform(get("/adminDashBoard/"))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error/403"));

    }


}
