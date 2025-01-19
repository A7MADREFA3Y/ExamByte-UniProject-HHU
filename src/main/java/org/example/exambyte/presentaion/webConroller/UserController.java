package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.application.dto.AnswersDto;
import org.example.exambyte.application.dto.TestDtoDisplayOnly;
import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.domain.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/userDashBoard")
public class UserController {

    private final ServiceInterface service;
    private final ServiceQuestionInterface serviceQuestion;

    public UserController(ServiceImp service, ServiceQuestionInterface serviceQuestion) {
        this.service = service;
        this.serviceQuestion = serviceQuestion;
    }


    @GetMapping("/")
    public String DashBoardUser(Authentication auth, HttpServletResponse response, Model model) {
        String username = service.getGithubUsername();
        List<Test> allTests = service.getAllTests();

        if(!(service.checkIfUser(auth) || service.checkIfAdmin(auth))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        model.addAttribute("allTestDtoDisplayOnly", service.getallTestDtoDisplayOnly(allTests));
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
        model.addAttribute("test", service.findTestById(testId));



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

        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        double theMCQPoints = 0;
        for (AnswerDto answer : answersDto.getAnswers()) {
            answer.setTestId(testId);
            answer.setTakenBy(service.getGithubUsername());

            theMCQPoints = service.getTheMCQPoints(answersDto,questions, testId);
            System.out.println(theMCQPoints);

            service.saveAnswer(answer);
        }

        TestResultDto testResultDto = TestResultDto.builder()
                .testId(testId)
                .takenBy(service.getGithubUsername())
                .answers(answersDto.getAnswers())
                .submitDate(LocalDateTime.now())
                .score(theMCQPoints)
                .passed(false)
                .graded(false)
                .build();

            service.saveTestResult(testResultDto);

        return "redirect:/userDashBoard/";
    }
}
