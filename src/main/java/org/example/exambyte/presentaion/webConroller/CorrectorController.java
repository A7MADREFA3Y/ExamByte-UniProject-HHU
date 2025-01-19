package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.application.service.serviceTest.ServiceInterface;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/correctorDashBoard")
public class CorrectorController {

    private final ServiceInterface service;

    public CorrectorController(ServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/")
    public String DashBoardCorrector(Authentication auth, HttpServletResponse response, Model model) {
        if(!(service.checkIfCorrector(auth) || (service.checkIfAdmin(auth)))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        model.addAttribute("username", service.getGithubUsername());
        return "CorrectorTemp/correctorDash";
    }
}
