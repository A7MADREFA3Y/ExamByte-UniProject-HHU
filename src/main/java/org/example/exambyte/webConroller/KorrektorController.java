package org.example.exambyte.webConroller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.exambyte.service.ServiceImp;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/KorrektorDashBoard")
public class KorrektorController {

    private ServiceImp service;

    public KorrektorController(ServiceImp service) {
        this.service = service;
    }

    @GetMapping("/")
    public String DashBoardKorrektor(Authentication auth, HttpServletResponse response) {

        if(!service.checkIfKorrektor(auth)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return "KorrektorTemp/korrektorDash";
    }
}
