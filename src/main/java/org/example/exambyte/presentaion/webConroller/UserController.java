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

    /***
     *
     * @param auth check if the login user have the right Authentication
     * @param response if the user don't have the permission that will return forbidden page
     * @param model display the username and the Test that are available
     * @return will return the user Dash Board
     */

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

    /***
     *
     * @param testId to use the id in the url and pass it to the next methode
     * @return confirm page to start the test
     */

    @GetMapping("/{testId}/gettingToTestPage")
    public String getToTestPage(@PathVariable Long testId) {
        return "UserTemp/preTest-TestPage";

    }

    /***
     *
     * @param model the username, test infos, every Question in the test, the Empty Answers and the submitted answers Before
     * @param testId to get the all the Question for that test
     * @return the page that display the Questions
     */

    @GetMapping("/{testId}/Start")
    public String startTest(Model model, @PathVariable("testId") Long testId) {
        String username = service.getGithubUsername();

        AnswersDto answersDto = new AnswersDto();
        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        for (Question question : questions) {
            AnswerDto answerDto = new AnswerDto();
            answerDto.setQuestionId(question.getId());
            answersDto.getAnswers().add(answerDto);
        }

        model.addAttribute("username", service.getGithubUsername());
        model.addAttribute("test", testService.findTestById(testId));
        model.addAttribute("questions", serviceQuestion.getAllQuestionByTestId(testId));
        model.addAttribute("answers", answersDto);
        model.addAttribute("submittedAnswers", answerService.getAllAnswersWithTestIdAndUsernameAsDto(testId, username));
        return "UserTemp/take-test";
    }

    /***
     *
     * @param answersDto is a List with submitted Answers As Dto to save them
     * @param testId to search for everything that used for this test get questions answers and test result
     * @return to the dashboard
     * this methode have code smells, and I don't have any idea how it works, but it does the job hahahahahaha
     */
    
    @PostMapping("/{testId}/Start")
    public String submitTest(@ModelAttribute("answers") AnswersDto answersDto,
                             @PathVariable("testId") Long testId) {

        String username = service.getGithubUsername();

        List<Question> questions = serviceQuestion.getAllQuestionByTestId(testId);
        double theMCQPoints = 0;
        int questionIndex = 0;
        for (AnswerDto answer : answersDto.getAnswers()) {
            answer.setTestId(testId);
            answer.setTakenBy(username);
            if (questions.get(questionIndex).getQuestionType() == QuestionType.MCQ) {
                answer.setCorrectedAnswer(questions.get(questionIndex).getCorrectAnswer());
            }
            if (answer.getAnswerText() == null || answer.getAnswerText().isEmpty()) {
                answer.setAnswerText("No answer provided !");
            }

            if (!(answerService.answerHaveBeenNOTSubmittedBefore(answer.getTakenBy(), answer.getQuestionId()))){
                answerService.saveAnswer(answer);
            }else{
                answerService.updateAnswer(answer);
            }
            questionIndex++;
        }
            theMCQPoints = service.getTheMCQPoints(answersDto,questions);

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
                TestResultDto testResultDtoUpdate = testResultService.updateTestResultWithNewAnswers(testId, username);
                testResultDtoUpdate.setScore(theMCQPoints);
                testResultDtoUpdate.setSubmitDate(LocalDateTime.now());
              testResultService.updateTestResultDto(testResultDtoUpdate);
            }

        return "redirect:/userDashBoard/";
    }


    /***
     *
     * @param model display the total Points of the test, all Questions, all submitted Answers and the Test Result
     * @param testId to get all the Test and submitted Answers Infos
     * @return page that table with all infos about the Test for the User to see the result
     */

    @GetMapping("/{testId}/SeeTheResults")
    public String seeTheResults(Model model, @PathVariable("testId") Long testId) {
        TestResult testResult = testResultService.getTestResultWithTestIdAndUsername(testId, service.getGithubUsername());
        List<Question> allQuestionByTestId = serviceQuestion.getAllQuestionByTestId(testId);
        List<Answer> allAnswersWithTestIdAndUsername = answerService.getAllAnswersWithTestIdAndUsername(testId, service.getGithubUsername());

        double totalPointForTheTest = 0;
        for (Question question : allQuestionByTestId) {
            if (question.getQuestionType() == QuestionType.MCQ) {
                totalPointForTheTest++;
            }else {
                totalPointForTheTest += 10;
            }
        }


        model.addAttribute("totalPointForTheTest", totalPointForTheTest);
        model.addAttribute("allQuestionByTestId", allQuestionByTestId);
        model.addAttribute("allAnswersWithTestIdAndUsername", allAnswersWithTestIdAndUsername);
        model.addAttribute("testResultDto", testResult);
        return "UserTemp/ResultsPage";
    }

}
