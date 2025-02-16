package org.example.exambyte.webConroller;

import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.application.service.testService.TestServiceInterface;
import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.helper.WithMockOAuth2User;
import org.example.exambyte.presentaion.webConroller.AdminController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
    ServiceQuestionInterface serviceQuestion;
    @MockitoBean
    ServiceInterface service;
    @MockitoBean
    TestServiceInterface testService;


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
                .param("startTime", LocalDateTime.now().plusDays(1).toString())
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
        TestsDto testsDto = new TestsDto();
        testsDto.setId(testId);

        testService.updateTestFromDto(testId, testsDto);


        mvc.perform(post("/adminDashBoard/{testId}/editTest" , testId)
                        .param("testName", "Mathe1")
                        .param("startTime", LocalDateTime.now().plusDays(1).toString())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminDashBoard/"));
    }

    @Test
    @DisplayName("editTestForm Test the url /{testId}/editTest Not Valid")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testEditTestFormTest() throws Exception {

        Long testId = 1L;

        mvc.perform(post("/adminDashBoard/{testId}/editTest" , testId)
                        .with(csrf())
                        .param("testName", "Mathe1")
                        .param("startTime", LocalDateTime.now().plusDays(1).toString()))
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

    @Test
    @DisplayName("QuestionCreator Test the url /{testId}/AddNewQuestion")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testAddNewQuestion() throws Exception {
        Long testId = 1L;
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(testId);

        when(testService.findTestById(testId)).thenReturn(test);

        mvc.perform(get("/adminDashBoard/{testId}/AddNewQuestion" , testId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("test"))
                .andExpect(view().name("AdminTemp/addNewQuestionPage"));
    }


    @Test
    @DisplayName("MCQuestionCreator test the url /{testId}/AddNewQuestion/MC")
    @WithMockOAuth2User(login = "admin", roles = "ADMIN")
    void testAddNewQuestionMC() throws Exception {
        Long testId = 1L;

        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(testId);
        when(testService.findTestById(testId)).thenReturn(test);

        mvc.perform(get("/adminDashBoard/{testId}/AddNewQuestion/MC" , testId)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("AdminTemp/addNewMCQuestionPage"));
    }

    @Test
    @DisplayName("MCQuestionCreator test the URL /{testId}/AddNewQuestion/MC")
    @WithMockUser(username = "admin", roles = "ADMIN") 
    void testAddNewQuestionMCTestV2() throws Exception {
        Long testId = 1L;

        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        when(testService.findTestById(testId)).thenReturn(test);

        mvc.perform(post("/adminDashBoard/{testId}/AddNewQuestion/MC", testId)
                        .with(csrf())
                        .param("questionText", "what is the ")
                        .param("option1", "true")
                        .param("option2", "false")
                        .param("correctAnswer", "A"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adminDashBoard/1/AddNewQuestion"));
    }


    @Test
    @DisplayName("FreeTextQuestionCreator methode tests the url /{testId}/AddNewQuestion/FreeText")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddNewQuestionFreeText() throws Exception {

        Long testId = 1L;
        org.example.exambyte.domain.model.Test test1 = new org.example.exambyte.domain.model.Test();
        test1.setId(testId);

        when(testService.findTestById(testId)).thenReturn(test1);

        mvc.perform(get("/adminDashBoard/{testId}/AddNewQuestion/FreeText", testId)
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("test"))
                .andExpect(model().attributeExists( "questionDto"))
                .andExpect(view().name("AdminTemp/addNewFREE_TEXTQuestionPage"));

    }

    @Test
    @DisplayName("FreeTextQuestionCreator mothode Post tests /{testId}/AddNewQuestion/FreeText ")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddNewQuestionFreeTextPost() throws Exception {
        Long testId = 1L;
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(testId);
        when(testService.findTestById(1L)).thenReturn(test);

        mvc.perform(post("/adminDashBoard/{testId}/AddNewQuestion/FreeText", testId)
                        .param("questionText", "what is the ")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
//                .andExpect(model().attributeExists("test"))
                .andExpect(redirectedUrl("/adminDashBoard/1/AddNewQuestion"));
    }


    @Test
    @DisplayName("seeAllTheQuestions mothode get tests /{testId}/GetAllQuestions")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAllQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();
        when(serviceQuestion.getAllQuestionByTestId(any())).thenReturn(questions);
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(1L);
        when(testService.findTestById(any())).thenReturn(test);

        mvc.perform(get("/adminDashBoard/{testId}/GetAllQuestions", 1L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists( "questions"))
                .andExpect(model().attributeExists( "testById"))
                .andExpect(view().name("AdminTemp/test-getAllQuestions"));
    }

    @Test
    @DisplayName("safeDeleteQuestion methode get tests /{testId}/{questionId}/safeDeleteQuestion")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testSafeDeleteQuestion() throws Exception {
        Long testId = 1L;
        Long questionId = 2L;
        Question question = new Question();
        question.setId(questionId);
        question.setTestId(testId);

        when(serviceQuestion.findQuestionById(questionId)).thenReturn(question);

        mvc.perform(get("/adminDashBoard/{testId}/{questionId}/safeDeleteQuestion", testId, questionId)
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("testId"))
                .andExpect(view().name("AdminTemp/Question-delete"));
    }

}
