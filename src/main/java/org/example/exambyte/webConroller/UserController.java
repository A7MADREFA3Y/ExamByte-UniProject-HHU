package org.example.exambyte.webConroller;

import org.example.exambyte.service.ServiceImp;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userDashBoard")
public class UserController {



    @GetMapping("/")
    public String DashBoardUser() {
        return "UserTemp/userDash";
    }
}
