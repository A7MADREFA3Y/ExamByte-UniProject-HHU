package org.example.exambyte.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.springframework.security.core.Authentication;
import org.example.exambyte.service.ServiceInterface;
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
        model.addAttribute("username", service.GetGithubAdminUsername());
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
        testsDto.setCreatedBy(service.GetGithubAdminUsername());


        service.saveTest(testsDto);
        return "redirect:/adminDashBoard/";
    }

    //    ----------------------------------------------------------------------------------------

    @GetMapping("/{testId}/SafeDeleteTest")
    public String safeDeleteRedirect(Model model, @PathVariable String testId) {
        model.addAttribute("username", service.GetGithubAdminUsername());
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



}





