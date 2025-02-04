package org.example.exambyte.webConroller;

import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.application.service.testService.TestServiceInterface;
import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.domain.model.TestResult;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.example.exambyte.presentaion.webConroller.AdminController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(status().isForbidden());

    }

    @Test
    @DisplayName("Testing createTestForm Url /adminDashBoard/newTest")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testCreateTestForm() throws Exception {
        mvc.perform(get("/adminDashBoard/newTest"))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminTemp/test-create"))
                .andExpect(model().attributeExists("test"));
    }

    @Test
    @DisplayName("SaveTest methode to Url /adminDashBoard/newTest")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testSaveTest() throws Exception {

        when(service.getGithubUsername()).thenReturn("username");

        mvc.perform(post("/adminDashBoard/newTest")
                .param("testName", "Mathe1")
                .param("startTime", LocalDateTime.now().toString())
                .param("endTime", LocalDateTime.now().plusDays(7).toString())
                .param("resultPublicationTime", LocalDateTime.now().plusDays(10).toString())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminDashBoard/"));
    }

    @Test
    @DisplayName("SaveTest methode to Url /adminDashBoard/newTest Not Valid")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testSaveTestNotValid() throws Exception {

        mvc.perform(post("/adminDashBoard/newTest")
                .with(csrf()))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @DisplayName("safeDeleteRedirect Test the Url /{testId}/SafeDeleteTest")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testSafeDeleteRedirect() throws Exception {
        Long testId = 1L;

        when(service.getGithubUsername()).thenReturn("username");

        mvc.perform(get("/adminDashBoard/{testId}/SafeDeleteTest" , testId)
                        .with(csrf()))
                .andExpect(model().attributeExists("username"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deleteTests Tests the Url /adminDashBoard/{testId}/deleteTest")
    @WithMockOAuth2User(login = "admin" , roles = "ADMIN")
    void testDeleteTests() throws Exception {
        Long testId = 1L;

        mvc.perform(post("/adminDashBoard/{testId}/deleteTest" , testId)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminDashBoard/"));
    }

    @Test
    @DisplayName("editTestForm the Url /adminDashBoard/{testId}/editTest")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testEditTestForm() throws Exception {
        Long testId = 1L;
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(testId);

        when(testService.findTestById(testId)).thenReturn(test);

        mvc.perform(get("/adminDashBoard/{testId}/editTest" , testId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminTemp/test-edit"))
                .andExpect(model().attributeExists("test"));
    }

    @Test
    @DisplayName("editTestForm Test the url /{testId}/editTest Not Valid")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testEditTestFormTest() throws Exception {

        Long testId = 1L;

        mvc.perform(post("/adminDashBoard/{testId}/editTest" , testId)
                        .with(csrf())
                        .param("testName", "Mathe1")
                        .param("startTime", LocalDateTime.now().toString())
                        .param("endTime", LocalDateTime.now().plusDays(7).toString())
                        .param("resultPublicationTime", LocalDateTime.now().plusDays(10).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminDashBoard/"));



    }


    @Test
    @DisplayName("editTestForm Test the url /{testId}/editTest Not Valid")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testEditTestFormTestNotValid() throws Exception {

        Long testId = 1L;

        mvc.perform(post("/adminDashBoard/{testId}/editTest" , testId)
                .with(csrf()))
                .andExpect(status().isOk());

    }





}
