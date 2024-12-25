package org.example.exambyte.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.service.ServiceImp;
import org.example.exambyte.service.ServiceInterface;
import org.example.exambyte.webSecurityConfig.AdminOnly;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminDashBoard")
@AdminOnly
public class AdminController {

    private ServiceImp service;

    public AdminController(ServiceImp service) {
        this.service = service;
    }


    @GetMapping("/")
    @AdminOnly
    public String DashBoardAdmin(Authentication auth, HttpServletResponse response) {

        if(!service.checkIfAdmin(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return "AdminTemp/adminDash";
    }

}
