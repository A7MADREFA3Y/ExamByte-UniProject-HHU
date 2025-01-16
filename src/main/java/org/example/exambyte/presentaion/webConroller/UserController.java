package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionInterface;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.example.exambyte.domain.model.Question;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

        if(!(service.checkIfUser(auth) || service.checkIfAdmin(auth))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        model.addAttribute("username", service.getGithubUsername());
        model.addAttribute("tests", service.getAllTests());


        return "UserTemp/userDash";
    }


    @GetMapping("/{testId}/Start")
    public String StartTest(Model model, @PathVariable("testId") Long testId) {

        model.addAttribute("username", service.getGithubUsername());
        model.addAttribute("test", service.findTestById(testId));
        model.addAttribute("allQuestions",serviceQuestion.getAllQuestionByTestId(testId));

        return "UserTemp/take-test";
    }
}
