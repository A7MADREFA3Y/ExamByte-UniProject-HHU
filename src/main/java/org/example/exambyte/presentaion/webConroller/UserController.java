package org.example.exambyte.presentaion.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userDashBoard")
public class UserController {

    private ServiceImp service;

    public UserController(ServiceImp service) {
        this.service = service;
    }


    @GetMapping("/")
    public String DashBoardUser(Authentication auth, HttpServletResponse response) {

        if(!(service.checkIfUser(auth) || service.checkIfAdmin(auth))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return "UserTemp/userDash";
    }
}
