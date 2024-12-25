package org.example.exambyte.webConroller;

import org.example.exambyte.webSecurityConfig.AdminOnly;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminDashBoard")
@AdminOnly
public class AdminController {


    @GetMapping("/")
    @AdminOnly
    public String DashBoardAdim() {
        return "AdminTemp/adminDash";
    }

}
