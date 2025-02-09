package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.application.service.answerService.AnswerServiceInterface;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.application.service.testResultService.TestResultServiceInterface;
import org.example.exambyte.application.service.testService.TestServiceInterface;
import org.example.exambyte.application.service.userService.UserServiceInterface;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.TestResult;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/correctorDashBoard")
public class CorrectorController {

    private final TestResultServiceInterface testResultService;
    private final AnswerServiceInterface answerService;
    private final UserServiceInterface userService;
    private final TestServiceInterface testService;
    private final ServiceInterface service;
    private final ServiceQuestionInterface questionService;

    public CorrectorController(TestResultServiceInterface testResultService, AnswerServiceInterface answerService, UserServiceInterface userService, TestServiceInterface testService, ServiceInterface service, ServiceQuestionInterface questionService) {
        this.testResultService = testResultService;
        this.answerService = answerService;
        this.userService = userService;
        this.testService = testService;
        this.service = service;
        this.questionService = questionService;
    }

    /***
     *
     * @param auth check if the user have the right Authentication
     * @param response if the user don't have the right Auth it will respond with forbidden
     * @param model get all the available tests
     * @return the correcter DashBoard
     */

    @GetMapping("/")
    public String DashBoardCorrector(Authentication auth, HttpServletResponse response, Model model) {
        if(!(userService.checkIfCorrector(auth) || (userService.checkIfAdmin(auth)))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "error/403";
        }

        model.addAttribute("tests", testService.getAllTests());
        model.addAttribute("username", service.getGithubUsername());
        return "CorrectorTemp/correctorDash";
    }


    /***
     *
     * @param testId to for each test results
     * @param model display the total points for the Test, Display the Test and the Test Result to be corrected
     * @return display all the Test result that are ready to be corrected
     */

    @GetMapping("/{testId}/gradingTheTest")
    public String gradingTheTest(@PathVariable Long testId, Model model) {
        String githubUsername = service.getGithubUsername();
        List<TestResult> getAllTestResults = testResultService.getAllTestResultsWithTestIdAndUsername(testId, githubUsername);


        int totalSizeForFreeText = questionService.getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(testId).size();
        int totalSizeForMCQ = questionService.getAllQuestionByTestId(testId).size() - totalSizeForFreeText;
        int totalPointsOfTest = totalSizeForFreeText * 10 + totalSizeForMCQ;

        model.addAttribute("totalPointsOfTest", totalPointsOfTest);
        model.addAttribute("test", testService.findTestById(testId));
        model.addAttribute("submittedList", getAllTestResults);
        return "CorrectorTemp/GradingTheTestPage";

    }

    /***
     *
     * @param testId searches for the Test
     * @param username to search for the Test Result and submitted Answer
     * @param currentIndex because of more than one List I created an Index to iterate with the List
     * @param model to display the index, corrected Answers, all the Questions, all the Answers and the Test result in the Front End
     * @return Display the Free Text Submitted Answers to give a feedback
     */

    @GetMapping("/{testId}/{username}/gradingTheTest")
    public String gradingTheTest(@PathVariable Long testId,
                                 @PathVariable String username,
                                 @RequestParam(value = "currentIndex", required = false, defaultValue = "0") int currentIndex,
                                 Model model) {

        List<Question> allQuestionByTestIdAndQuestionType = questionService.getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(testId);
        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDto = answerService.getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(testId, username);
        TestResult testResult = testResultService.getTestResultWithTestIdAndUsername(testId, username);

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

        List<Integer> totalPoints = new ArrayList<>(Collections.nCopies(allQuestionByTestIdAndQuestionType.size(), 0));
        model.addAttribute("totalPoints", totalPoints);


        return "CorrectorTemp/GradingEachTestPage";
    }


    /***
     *
     * @param testId .
     * @param username .
     * @param correctedAnswers .
     * @param totalPoints .
     * @param model .
     * @param redirectAttributes .
     * @return .
     * this methode need to refactor to the Service Layer and have don't have SPR and hard to test, but it won't break
     * because I know my coding skills plus I don't have any clue how it works, so please don't touch it
     */


    @PostMapping("/{testId}/{username}/gradingTheTest")
    public String updateGradingTheTest(@PathVariable Long testId,
                                       @PathVariable String username,
                                       @RequestParam List<String> correctedAnswers, // Receive all corrected answers
                                       @RequestParam List<Integer> totalPoints,
                                       Model model, RedirectAttributes redirectAttributes) {

        if (totalPoints == null || totalPoints.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "You must assign points to all answers!");
            return "redirect:/correctorDashBoard/{testId}/{username}/gradingTheTest";
        }

        // Get all questions and answers (this would normally be fetched from the database)
        List<Question> allQuestions = questionService.getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(testId);
        List<AnswerDto> allAnswers = answerService.getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(testId, username);
        // Loop through the corrected answers and update each corresponding answer
        for (int i = 0; i < correctedAnswers.size(); i++) {
            if (i < allAnswers.size()) {
                allAnswers.get(i).setCorrectedAnswer(correctedAnswers.get(i));
            }
        }

        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDto = answerService.getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(testId, username);
        int i = 0;
        for (String correctedAnswer : correctedAnswers) {
            allAnswersWithTestIdAndUsernameAsDto.get(i).setCorrectedAnswer(correctedAnswer);
            answerService.updateAnswer(allAnswersWithTestIdAndUsernameAsDto.get(i));
             i += 1;

        }

        String takeBy = allAnswersWithTestIdAndUsernameAsDto.getFirst().getTakenBy();
        TestResult testResultWithTestIdAndUsername = testResultService.getTestResultWithTestIdAndUsername(testId, takeBy);

        Double sum = totalPoints.stream().mapToDouble(Integer::intValue).sum();
        double toBeAddedPoints = testResultWithTestIdAndUsername.getGrade() + sum;
        int numberOfFreeTextQuestions = allQuestions.size() * 10;
        int numberOfMCQuestions = (questionService.getAllQuestionByTestId(testId).size() - allQuestions.size());
        int sumOfBothFreeTextAndMCQuestions = numberOfFreeTextQuestions + numberOfMCQuestions;

        if (((double) sumOfBothFreeTextAndMCQuestions / 2) <= toBeAddedPoints  ){
            testResultWithTestIdAndUsername.setPassed(true);
        }else {
            testResultWithTestIdAndUsername.setPassed(false);
        }
        testResultWithTestIdAndUsername.setGrade(toBeAddedPoints);
        testResultService.updateTestResult(testResultWithTestIdAndUsername);

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
