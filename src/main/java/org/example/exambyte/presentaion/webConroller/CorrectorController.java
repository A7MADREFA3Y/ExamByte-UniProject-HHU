package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.TestResult;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

        model.addAttribute("tests", service.getAllTests());
        model.addAttribute("username", service.getGithubUsername());
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
    public String gradingTheTest(@PathVariable Long testId,
                                 @PathVariable String username,
                                 @RequestParam(value = "currentIndex", required = false, defaultValue = "0") int currentIndex,
                                 Model model) {

        List<Question> allQuestionByTestIdAndQuestionType = questionService.getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(testId);
        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDto = service.getAllAnswersWithTestIdAndUsernameAsDto(testId, username);
        TestResult testResult = service.getTestResultWithTestIdAndUsername(testId, username);

        List<String> correctedAnswers = new ArrayList<>(); // To hold all corrected answers

        // Initialize correctedAnswers list on first load
        for (int i = 0; i < allQuestionByTestIdAndQuestionType.size(); i++) {
            correctedAnswers.add(""); // Add placeholders for corrected answers
        }

        // Ensure the currentIndex is within bounds
        if (currentIndex < 0) currentIndex = 0;
        if (currentIndex >= allQuestionByTestIdAndQuestionType.size()) currentIndex = allQuestionByTestIdAndQuestionType.size() - 1;

        // Pass the attributes to the model
        model.addAttribute("correctedAnswers", correctedAnswers);
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("totalQuestions", allQuestionByTestIdAndQuestionType.size()); // Set totalQuestions
        model.addAttribute("questions", allQuestionByTestIdAndQuestionType);
        model.addAttribute("answers", allAnswersWithTestIdAndUsernameAsDto);
        model.addAttribute("testResult", testResult);

        return "CorrectorTemp/GradingEachTestPage";
    }


    @PostMapping("/{testId}/{username}/gradingTheTest")
    public String updateGradingTheTest(@PathVariable Long testId,
                                       @PathVariable String username,
                                       @RequestParam List<String> correctedAnswers, // Receive all corrected answers
                                       Model model, RedirectAttributes redirectAttributes) {

        // Get all questions and answers (this would normally be fetched from the database)
        List<Question> allQuestions = questionService.getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(testId);
        List<AnswerDto> allAnswers = service.getAllAnswersWithTestIdAndUsernameAsDto(testId, username);

        // Loop through the corrected answers and update each corresponding answer
        for (int i = 0; i < correctedAnswers.size(); i++) {
            if (i < allAnswers.size()) {
                allAnswers.get(i).setCorrectedAnswer(correctedAnswers.get(i));
            }
        }

        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDto = service.getAllAnswersWithTestIdAndUsernameAsDto(testId, username);
        int i = 0;
        for (String correctedAnswer : correctedAnswers) {
            allAnswersWithTestIdAndUsernameAsDto.get(i).setCorrectedAnswer(correctedAnswer);
            service.updateAnswer(allAnswersWithTestIdAndUsernameAsDto.get(i));
             i += 1;

        }

        String takeBy = allAnswersWithTestIdAndUsernameAsDto.getFirst().getTakenBy();
        TestResult testResultWithTestIdAndUsername = service.getTestResultWithTestIdAndUsername(testId, takeBy);
        service.updateTestResult(testResultWithTestIdAndUsername);

        // Add necessary attributes back to the model
        model.addAttribute("testId", testId);
        model.addAttribute("username", username);
        model.addAttribute("correctedAnswers", correctedAnswers);
        model.addAttribute("currentIndex", 0);  // Start at the first question
        model.addAttribute("totalQuestions", allQuestions.size());
        model.addAttribute("questions", allQuestions);
        model.addAttribute("answers", allAnswers);

        redirectAttributes.addFlashAttribute("message", "All answers have been submitted and corrected.");





        // Return the same page to display the updated information
        return "redirect:/correctorDashBoard/{testId}/gradingTheTest?testId=" + testId;
    }







}
