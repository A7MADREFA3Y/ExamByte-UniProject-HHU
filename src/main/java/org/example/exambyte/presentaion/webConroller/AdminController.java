package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.QuestionType;
import org.example.exambyte.domain.model.Test;
import org.springframework.security.core.Authentication;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.exambyte.domain.model.QuestionType.FREE_TEXT;
import static org.example.exambyte.domain.model.QuestionType.MCQ;


@Controller
@RequestMapping("/adminDashBoard")
//@PreAuthorize("ADMIN")
public class AdminController {

    private final ServiceInterface service;
    private final ServiceQuestionInterface serviceQuestion;

    public AdminController(ServiceInterface service, ServiceQuestionInterface serviceQuestion) {
        this.service = service;
        this.serviceQuestion = serviceQuestion;
    }

//    ----------------------------------------------------------------------------------------

    @GetMapping("/")
    public String DashBoardAdmin(Authentication auth, Model model,
                                 HttpServletResponse response) {

        if(!service.checkIfAdmin(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        model.addAttribute("username", service.getGithubUsername());
        model.addAttribute("tests", service.getAllTests());
        return "AdminTemp/adminDash";
    }

//    ----------------------------------------------------------------------------------------

    @GetMapping("/newTest")
    public String createTestForm(Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
        return "AdminTemp/test-create";
    }

    @PostMapping("/newTest")
    public String SaveTest(@Valid @ModelAttribute("test") TestsDto testsDto,
                           Model model,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("test", testsDto);
            return "AdminTemp/test-create";
        }


        //to set as default how created this test
        testsDto.setCreatedBy(service.getGithubUsername());


        service.saveTest(testsDto);
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
        service.deleteTest(testId);
        return "redirect:/adminDashBoard/";
    }

//    ----------------------------------------------------------------------------------------

    @GetMapping("/{testId}/editTest")
    public String editTestForm(@PathVariable("testId") Long testId, Model model) {
        Test test = service.findTestById(testId);
        model.addAttribute("test", test);
        return "AdminTemp/test-edit";
    }

    @PostMapping("/{testId}/editTest")
    public String editTestForm(
            @PathVariable Long testId,
            @ModelAttribute("test") @Valid TestsDto testsDto,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("test", testsDto);
            return "AdminTemp/test-edit"; // Return to the form if there are validation errors
        }

        // Call the service to update the test
        service.updateTestFromDto(testId, testsDto);

        // Redirect to the dashboard or success page
        return "redirect:/adminDashBoard/";
    }

    //    ----------------------------------------------------------------------------------------

    @GetMapping("/{testId}/AddNewQuestion")
    public String QuestionCreator(@PathVariable("testId") Long testId, Model model) {
//        find test by id to add question to the test
        Test test = service.findTestById(testId);
        model.addAttribute("test", test);

       return "AdminTemp/addNewQuestionPage";
    }

    //    ----------------------------------------------------------------------------------------


    @GetMapping("/{testId}/AddNewQuestion/MC")
    public String MCQuestionCreator(@PathVariable("testId") Long testId, Model model) {

        Test test = service.findTestById(testId);
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

        Test test = service.findTestById(testId);
        model.addAttribute("test", test);

        serviceQuestion.saveQuestion(questionDto);

        return "redirect:/adminDashBoard/{testId}/AddNewQuestion";
    }

    //    ----------------------------------------------------------------------------------------


    @GetMapping("/{testId}/AddNewQuestion/FreeText")
    public String FreeTextQuestionCreator(@PathVariable("testId") Long testId, Model model) {

        Test test = service.findTestById(testId);
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

        Test test = service.findTestById(testId);
        model.addAttribute("test", test);

        serviceQuestion.saveQuestion(questionDto);

        return "redirect:/adminDashBoard/{testId}/AddNewQuestion";
    }

    //    ----------------------------------------------------------------------------------------





    @GetMapping("/{testId}/{questionId}/AddNewQuestion")
    private String MCQuestionCreator(@PathVariable("testId") Long testId, @PathVariable("questionId") Long questionId) {
        Test test = service.findTestById(testId);
        Question question = serviceQuestion.findQuestionById(questionId);
        return "AdminTemp/questionTypeChosePage";
    }



    @PostMapping("/{testId}/AddNewQuestion")
    public String QuestionCreator(Model model) {
        return "AdminTemp/addNewQuestionPage";
    }



}





