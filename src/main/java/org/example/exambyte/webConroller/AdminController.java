package org.example.exambyte.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.springframework.security.core.Authentication;
import org.example.exambyte.service.ServiceInterface;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adminDashBoard")
//@PreAuthorize("ADMIN")
public class AdminController {

    private final ServiceInterface service;

    public AdminController(ServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/")
    public String DashBoardAdmin(Authentication auth, Model model,
                                 HttpServletResponse response) {

        if(!service.checkIfAdmin(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        model.addAttribute("tests", service.getAllTests());

        return "AdminTemp/adminDash";
    }



    @GetMapping("/newTest")
    public String createTestForm(Model model) {

        Test test = new Test();
        model.addAttribute("test", test);
        return "AdminTemp/create-Test";
    }

    @PostMapping("/newTest")
    public String SaveTest(@ModelAttribute("test") TestsDto testsDto,
                           Model model, BindingResult bindingResult) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String githubUsername = (String) oauth2User.getAttributes().get("login"); // GitHub username

        // Set the GitHub username to 'createdBy'
        testsDto.setCreatedBy(githubUsername);

//        if(bindingResult.hasErrors()) {
//            System.out.println("2");
//
//            model.addAttribute("test", testsDto);
//            return "AdminTemp/create-Test";
//        }

        service.saveTest(testsDto);
        return "redirect:/adminDashBoard/";
    }

}
