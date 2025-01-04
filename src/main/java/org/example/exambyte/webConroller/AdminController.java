package org.example.exambyte.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.springframework.security.core.Authentication;
import org.example.exambyte.service.ServiceInterface;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/adminDashBoard")
//@PreAuthorize("ADMIN")
public class AdminController {

    private final ServiceInterface service;

    public AdminController(ServiceInterface service) {
        this.service = service;
    }

//    ----------------------------------------------------------------------------------------

    @GetMapping("/")
    public String DashBoardAdmin(Authentication auth, Model model,
                                 HttpServletResponse response) {

        if(!service.checkIfAdmin(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        model.addAttribute("tests", service.getAllTests());
        return "AdminTemp/adminDash";
    }

//    ----------------------------------------------------------------------------------------

    @GetMapping("/newTest")
    public String createTestForm( Model model) {
        Test test = new Test();
        model.addAttribute("test", test);
        return "AdminTemp/create-Test";
    }

    @PostMapping("/newTest")
    public String SaveTest(@Valid @ModelAttribute("test") TestsDto testsDto,
                           Model model,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("test", testsDto);
            return "AdminTemp/create-Test";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String githubUsername = (String) oauth2User.getAttributes().get("login");
        if (githubUsername == null) {
            throw new IllegalStateException("GitHub username is not available");
        }

        //to set as default how created this test
        testsDto.setCreatedBy(githubUsername);

        service.saveTest(testsDto);
        return "redirect:/adminDashBoard/";
    }

    //    ----------------------------------------------------------------------------------------

    @GetMapping("/{testId}/deleteTest")
    private String deleteTest(@PathVariable("testId") Long testId) {
        service.delete(testId);
        return "redirect:/adminDashBoard/";
    }
}










