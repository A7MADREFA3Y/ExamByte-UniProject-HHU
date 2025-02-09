package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.testService.TestServiceInterface;
import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.Test;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.exambyte.domain.model.QuestionType.FREE_TEXT;
import static org.example.exambyte.domain.model.QuestionType.MCQ;


@Controller
@RequestMapping("/adminDashBoard")
public class AdminController {

    private final UserServiceInterface userServiceInterface;
    private final ServiceInterface service;
    private final TestServiceInterface testService;
    private final ServiceQuestionInterface serviceQuestion;

    public AdminController(UserServiceInterface userServiceInterface, ServiceInterface service, TestServiceInterface testService, ServiceQuestionInterface serviceQuestion) {
        this.userServiceInterface = userServiceInterface;
        this.service = service;
        this.testService = testService;
        this.serviceQuestion = serviceQuestion;
    }

//    ----------------------------------------------------------------------------------------

    /***
     *
     * @param auth check if the User have the right auth to use this Url
     * @param model the tests that Admin created and the Username to welcome the user in the page
     * @param response if the user don't have the right auth Values the response will be forbidden
     * @return the manin admin Dash Board
     */
    @GetMapping("/")
    public String DashBoardAdmin(Authentication auth, Model model,
                                 HttpServletResponse response) {

        if(!userServiceInterface.checkIfAdmin(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        
        model.addAttribute("username", service.getGithubUsername());
        model.addAttribute("tests", testService.getAllTests());
        return "AdminTemp/adminDash";
    }

//    ----------------------------------------------------------------------------------------

    /***
     *
     * @param model create Empty Test to pass in the postMapping
     * @return the Create Test Page
     */

    @GetMapping("/newTest")
    public String createTestForm(Model model) {
        TestsDto test = new TestsDto();
        model.addAttribute("test", test);
        return "AdminTemp/test-create";
    }

    /***
     *
     * @param testsDto the Empty Test that created in the GetMapping
     * @param bindingResult check if the Test is Valid
     * @param model if the test is not valid it displays the Empty Test again
     * @return to the admin dash Board
     */

    @PostMapping("/newTest")
    public String SaveTest(@Valid @ModelAttribute("test") TestsDto testsDto,
                           Model model,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors() || testsDto.getStartTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("test", testsDto);
            return "AdminTemp/test-create";
        }

        testsDto.setCreatedBy(service.getGithubUsername());
        testsDto.setEndTime(testsDto.getStartTime().plusDays(7));
        testsDto.setResultPublicationTime(testsDto.getStartTime().plusDays(14));
        testService.saveTest(testsDto);
        return "redirect:/adminDashBoard/";
    }

    //    ----------------------------------------------------------------------------------------

    /***
     *
     * @param model to display the Username
     * @param testId to use the Test ID in the Url then delete this specific  test
     * @return a Page to ask if you are sure to delete this test
     */

    @GetMapping("/{testId}/SafeDeleteTest")
    public String safeDeleteRedirect(Model model, @PathVariable String testId) {
        model.addAttribute("username", service.getGithubUsername());
        return "AdminTemp/test-delete";
    }

    /***
     *
     * @param testId takes the test ID to search for this specific Test to delete it
     * @return to the admin Dash Board
     */

    @PostMapping("/{testId}/deleteTest")
    public String deleteTests(@PathVariable("testId") Long testId) {
        testService.deleteTest(testId);
        return "redirect:/adminDashBoard/";
    }

//    ----------------------------------------------------------------------------------------

    /***
     *
     * @param testId takes the ID in the URl and find the Test ID
     * @param model display the old infos of the Test
     * @return a Page that edit the
     */

    @GetMapping("/{testId}/editTest")
    public String editTestForm(@PathVariable("testId") Long testId, Model model) {
        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);
        return "AdminTemp/test-edit";
    }

    /***
     *
     * @param testId takes the Test ID to update/Edit the Test infos
     * @param testsDto use dto to change the infos then convert to entity later
     * @param bindingResult checks if the test Valid
     * @param model if the test not valid it will view the old Test
     * @return after edit the test it will return to the Admin Dash Board
     */

    @PostMapping("/{testId}/editTest")
    public String editTestForm(
            @PathVariable Long testId,
            @ModelAttribute("test") @Valid TestsDto testsDto,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors() || testsDto.getStartTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("test", testsDto);
            return "AdminTemp/test-edit";
        }

        testsDto.setEndTime(testsDto.getStartTime().plusDays(7));
        testsDto.setResultPublicationTime(testsDto.getStartTime().plusDays(14));

        // Call the service to update the test
        testService.updateTestFromDto(testId, testsDto);

        // Redirect to the dashboard or success page
        return "redirect:/adminDashBoard/";
    }

    //    ----------------------------------------------------------------------------------------

    /***
     *
     * @param testId used to search for specific test
     * @param model to pass the test to the next methode
     * @return return the add new Question Page to chose what kind of Question to be added
     */

    @GetMapping("/{testId}/AddNewQuestion")
    public String QuestionCreator(@PathVariable("testId") Long testId, Model model) {
//        find test by id to add question to the test
        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

       return "AdminTemp/addNewQuestionPage";
    }

    //    ----------------------------------------------------------------------------------------

    /***
     *
     * @param testId to find the Test to add the Multiple choice question
     * @param model to pin the test and create new Empty Question
     * @return the Add New  Multiple choice question Page to create the question
     */

    @GetMapping("/{testId}/AddNewQuestion/MC")
    public String MCQuestionCreator(@PathVariable("testId") Long testId, Model model) {

        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

        QuestionDto questionDto = new QuestionDto();
        model.addAttribute("questionDto", questionDto);


        return "AdminTemp/addNewMCQuestionPage";
    }

    /***
     *
     * @param testId to set the test ID for the Question
     * @param model to pin the Question with the Test
     * @param questionDto use the Dto to fill the Question infos
     * @param bindingResult check if the Question is valid
     * @return to the page that before the creation new question
     */

    @PostMapping("/{testId}/AddNewQuestion/MC")
    public String MCQuestionCreator(@PathVariable("testId") Long testId, Model model,
                                    @ModelAttribute("questionDto") QuestionDto questionDto,
                                    BindingResult bindingResult) {

        questionDto.setQuestionType(MCQ);
        questionDto.setTestId(testId);

        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

        serviceQuestion.saveQuestion(questionDto);

        return "redirect:/adminDashBoard/{testId}/AddNewQuestion";
    }

    //    ----------------------------------------------------------------------------------------

    /***
     *
     * @param testId to find the Test to add the Multiple choice question
     * @param model to pin the test and create new Empty Question
     * @return
     */

    @GetMapping("/{testId}/AddNewQuestion/FreeText")
    public String FreeTextQuestionCreator(@PathVariable("testId") Long testId, Model model) {

        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

        QuestionDto questionDto = new QuestionDto();
        model.addAttribute("questionDto", questionDto);


        return "AdminTemp/addNewFREE_TEXTQuestionPage";
    }

    /***
     *
     * @param testId to set the Test ID with the Question
     * @param model to pin the Question with the Test
     * @param questionDto the Empty Question form the GetMapping
     * @param bindingResult checks if the Questions is Valid
     * @return to the page to choice the new Question
     */

    @PostMapping("/{testId}/AddNewQuestion/FreeText")
    public String FreeTextQuestionCreator(@PathVariable("testId") Long testId, Model model,
                                    @ModelAttribute("questionDto") QuestionDto questionDto,
                                    BindingResult bindingResult) {

        questionDto.setQuestionType(FREE_TEXT);
        questionDto.setTestId(testId);

        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

        serviceQuestion.saveQuestion(questionDto);

        return "redirect:/adminDashBoard/{testId}/AddNewQuestion";
    }

    //    ----------------------------------------------------------------------------------------


    /***
     *
     * @param testId to search for All questions that have the same Test ID
     * @param model to Display all the Questions and Tests
     * @return a Test page that have all the Questions in it
     */

    @GetMapping("/{testId}/GetAllQuestions")
    public String seeAllTheQuestions(@PathVariable("testId") Long testId, Model model) {

        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        model.addAttribute("questions", questions);

        long testById = testService.findTestById(testId).getId();
        model.addAttribute("testById", testById);

        return "AdminTemp/test-getAllQuestions";
    }

    //    ----------------------------------------------------------------------------------------

    /***
     *
     * @param questionId to search for specific question
     * @param testId to pass the test id to make sure
     * @param model to select the Question before delete
     * @return page to confirm the Delete
     */

//    new methode not tested
    @GetMapping("/{testId}/{questionId}/safeDeleteQuestion")
    public String safeDeleteQuestion(@PathVariable("questionId") Long questionId, @PathVariable("testId") Long testId, Model model ) {
        Question questionById = serviceQuestion.findQuestionById(questionId);
        model.addAttribute( "testId", questionById.getTestId());
        return "AdminTemp/Question-delete";
    }

    /***
     *
     * @param questionId to Delete the Selected Question
     * @param testId to return to the page that same test
     * @return the Test page that have all the questions in it 
     */

    @PostMapping("/{testId}/{questionId}/deleteQuestion")
    public String deleteQuestion(@PathVariable("questionId") Long questionId, @PathVariable("testId") Long testId) {

        serviceQuestion.deleteQuestionById(questionId);

        return "redirect:/adminDashBoard/" + testId + "/GetAllQuestions";

    }

}





