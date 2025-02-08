package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.application.dto.AnswersDto;
import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.application.service.answerService.AnswerServiceInterface;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.application.service.testResultService.TestResultServiceInterface;
import org.example.exambyte.application.service.testService.TestServiceInterface;
import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.domain.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/userDashBoard")
public class UserController {

    private final AnswerServiceInterface answerService;
    private final TestServiceInterface testService;
    private final UserServiceInterface userService;
    private final ServiceInterface service;
    private final ServiceQuestionInterface serviceQuestion;
    private final TestResultServiceInterface testResultService;

    public UserController(AnswerServiceInterface answerService, TestServiceInterface testService, UserServiceInterface userService, ServiceImp service, ServiceQuestionInterface serviceQuestion, TestResultServiceInterface testResultService) {
        this.answerService = answerService;
        this.testService = testService;
        this.userService = userService;
        this.service = service;
        this.serviceQuestion = serviceQuestion;
        this.testResultService = testResultService;
    }


    @GetMapping("/")
    public String DashBoardUser(Authentication auth, HttpServletResponse response, Model model) {
        String username = service.getGithubUsername();
        List<Test> allTests = testService.getAllTests();

        if(!(userService.checkIfUser(auth) || userService.checkIfAdmin(auth))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "error/403";
        }

        model.addAttribute("allTestDtoDisplayOnly", service.getAllTestDtoDisplayOnly(allTests));
        model.addAttribute("username", username);


        return "UserTemp/userDash";
    }


    @GetMapping("/{testId}/gettingToTestPage")
    public String getToTestPage(Model model, @PathVariable Long testId) {
        return "UserTemp/preTest-TestPage";

    }


    @GetMapping("/{testId}/Start")
    public String startTest(Model model, @PathVariable("testId") Long testId) {
        model.addAttribute("questions", serviceQuestion.getAllQuestionByTestId(testId));
        model.addAttribute("username", service.getGithubUsername());
        model.addAttribute("test", testService.findTestById(testId));



        AnswersDto answersDto = new AnswersDto();
        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        for (Question question : questions) {
            AnswerDto answerDto = new AnswerDto();
            answerDto.setQuestionId(question.getId());
            answersDto.getAnswers().add(answerDto);
        }

        model.addAttribute("answers", answersDto);
        return "UserTemp/take-test";
    }


    @PostMapping("/{testId}/Start")
    public String submitTest(@ModelAttribute("answers") AnswersDto answersDto,
                             @PathVariable("testId") Long testId) {

        String username = service.getGithubUsername();

        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        double theMCQPoints = 0;
        for (AnswerDto answer : answersDto.getAnswers()) {
            answer.setTestId(testId);
            answer.setTakenBy(username);
            if (answer.getAnswerText() == null || answer.getAnswerText().isEmpty()) {
                answer.setAnswerText("No answer provided !");
            }
            theMCQPoints = service.getTheMCQPoints(answersDto,questions, testId);

            if (!(answerService.answerHaveBeenNOTSubmittedBefore(answer.getTakenBy(), answer.getQuestionId()))){
                answerService.saveAnswer(answer);
            }else{
                answerService.updateAnswer(answer);
            }
        }

            if (testResultService.getTestResultWithTestIdAndUsername(testId, username) == null) {
                TestResultDto testResultDto = TestResultDto.builder()
                        .testId(testId)
                        .takenBy(service.getGithubUsername())
                        .answers(answersDto.getAnswers())
                        .submitDate(LocalDateTime.now())
                        .score(theMCQPoints)
                        .passed(false)
                        .graded(false)
                        .build();

                    testResultService.saveTestResult(testResultDto);

            }else {
                TestResultDto testResultDtoupdate = testResultService.updateTestResultWithNewAnswers(testId, username);
                testResultDtoupdate.setScore(theMCQPoints);
                testResultDtoupdate.setSubmitDate(LocalDateTime.now());
              testResultService.updateTestResultDto(testResultDtoupdate);
            }

        return "redirect:/userDashBoard/";
    }

    @GetMapping("/{testId}/SeeTheResults")
    public String seeTheResults(Model model, @PathVariable("testId") Long testId) {
        TestResult testResult = testResultService.getTestResultWithTestIdAndUsername(testId, service.getGithubUsername());
        List<Question> allQuestionByTestId = serviceQuestion.getAllQuestionByTestId(testId);
        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDto = answerService.getAllAnswersWithTestIdAndUsernameAsDto(testId, service.getGithubUsername());

        model.addAttribute("testResultDto", testResult);
        model.addAttribute("allQuestionByTestId", allQuestionByTestId);
        model.addAttribute("allAnswersWithTestId", allAnswersWithTestIdAndUsernameAsDto);
        return "UserTemp/ResultsPage";
    }

}
