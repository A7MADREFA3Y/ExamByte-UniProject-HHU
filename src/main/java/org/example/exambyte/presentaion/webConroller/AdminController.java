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
//@PreAuthorize("ADMIN")
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

    @GetMapping("/newTest")
    public String createTestForm(Model model) {
        TestsDto test = new TestsDto();
        model.addAttribute("test", test);
        return "AdminTemp/test-create";
    }

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

    @GetMapping("/{testId}/SafeDeleteTest")
    public String safeDeleteRedirect(Model model, @PathVariable String testId) {
        model.addAttribute("username", service.getGithubUsername());
        return "AdminTemp/test-delete";
    }


    @PostMapping("/{testId}/deleteTest")
    public String deleteTests(@PathVariable("testId") Long testId) {
        testService.deleteTest(testId);
        return "redirect:/adminDashBoard/";
    }

//    ----------------------------------------------------------------------------------------

    @GetMapping("/{testId}/editTest")
    public String editTestForm(@PathVariable("testId") Long testId, Model model) {
        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);
        return "AdminTemp/test-edit";
    }

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

    @GetMapping("/{testId}/AddNewQuestion")
    public String QuestionCreator(@PathVariable("testId") Long testId, Model model) {
//        find test by id to add question to the test
        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

       return "AdminTemp/addNewQuestionPage";
    }

    //    ----------------------------------------------------------------------------------------


    @GetMapping("/{testId}/AddNewQuestion/MC")
    public String MCQuestionCreator(@PathVariable("testId") Long testId, Model model) {

        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

        QuestionDto questionDto = new QuestionDto();
        model.addAttribute("questionDto", questionDto);


        return "AdminTemp/addNewMCQuestionPage";
    }

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


    @GetMapping("/{testId}/AddNewQuestion/FreeText")
    public String FreeTextQuestionCreator(@PathVariable("testId") Long testId, Model model) {

        Test test = testService.findTestById(testId);
        model.addAttribute("test", test);

        QuestionDto questionDto = new QuestionDto();
        model.addAttribute("questionDto", questionDto);


        return "AdminTemp/addNewFREE_TEXTQuestionPage";
    }

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


    @GetMapping("/{testId}/GetAllQuestions")
    public String seeAllTheQuestions(@PathVariable("testId") Long testId, Model model) {

        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        model.addAttribute("questions", questions);

        long testById = testService.findTestById(testId).getId();
        model.addAttribute("testById", testById);

        return "AdminTemp/test-getAllQuestions";
    }

    //    ----------------------------------------------------------------------------------------



//    new methode not tested
    @GetMapping("/{testId}/{questionId}/safeDeleteQuestion")
    public String safeDeleteQuestion(@PathVariable("questionId") Long questionId, @PathVariable("testId") Long testId, Model model ) {
        Question questionById = serviceQuestion.findQuestionById(questionId);
        model.addAttribute( "testId", questionById.getTestId());
        return "AdminTemp/Question-delete";
    }

    @PostMapping("/{testId}/{questionId}/deleteQuestion")
    public String deleteQuestion(@PathVariable("questionId") Long questionId, @PathVariable("testId") Long testId) {

        serviceQuestion.deleteQuestionById(questionId);

        return "redirect:/adminDashBoard/" + testId + "/GetAllQuestions";

    }











        //    ----------------------------------------------------------------------------------------







//    @GetMapping("/{testId}/{questionId}/AddNewQuestion")
//    private String MCQuestionCreator(@PathVariable("testId") Long testId, @PathVariable("questionId") Long questionId) {
//        Test test = testService.findTestById(testId);
//        Question question = serviceQuestion.findQuestionById(questionId);
//        return "AdminTemp/questionTypeChosePage";
//    }


//
//    @PostMapping("/{testId}/AddNewQuestion")
//    public String QuestionCreator(Model model) {
//        return "AdminTemp/addNewQuestionPage";
//    }



}





