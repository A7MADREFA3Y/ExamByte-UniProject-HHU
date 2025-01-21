package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.TestResult;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/correctorDashBoard")
public class CorrectorController {

    private final ServiceInterface service;
    private final ServiceQuestionInterface questionService;

    public CorrectorController(ServiceInterface service, ServiceQuestionInterface questionService) {
        this.service = service;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String DashBoardCorrector(Authentication auth, HttpServletResponse response, Model model) {
        if(!(service.checkIfCorrector(auth) || (service.checkIfAdmin(auth)))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        String githubUsername = service.getGithubUsername();
        Long testid = 5L;

        List<Answer> allAnswersForTest = service.getAllAnswersWithTestIdAndUsername(testid, githubUsername);


        model.addAttribute("tests", service.getAllTests());
        model.addAttribute("username", githubUsername);
        return "CorrectorTemp/correctorDash";
    }


    @GetMapping("/{testId}/gradingTheTest")
    public String gradingTheTest(@PathVariable Long testId, Model model) {
        String githubUsername = service.getGithubUsername();
        List<TestResult> getAllTestResults = service.getAllTestResultsWithTestIdAndUsername(testId, githubUsername);


        model.addAttribute("test", service.findTestById(testId));
        model.addAttribute("submittedList", getAllTestResults);
        return "CorrectorTemp/GradingTheTestPage";

    }

    @GetMapping("/{testId}/{username}/gradingTheTest")
    public String gradingTheTest(@PathVariable Long testId, @PathVariable String username, Model model) {

        List<Answer> allAnswersWithTestIdAndUsername = service.getAllAnswersWithTestIdAndUsername(testId, username);
        List<Question> allQuestionByTestIdAndQuestionType = questionService.getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(testId);


        model.addAttribute("questions", allQuestionByTestIdAndQuestionType);
        model.addAttribute("answers", allAnswersWithTestIdAndUsername);
        return "CorrectorTemp/GradingEachTestPage";
    }

    @PostMapping("/{testId}/{username}/gradingTheTest")
    public String updateGradingTheTest(@PathVariable Long testId, @PathVariable String username, Model model) {
        TestResult testResult = service.getTestResultWithTestIdAndUsername(testId, username);

        testResult.setCorrectedBy(service.getGithubUsername());





        model.addAttribute("testResult", testResult);
        return "CorrectorTemp/GradingTheTestPage";
    }







}
