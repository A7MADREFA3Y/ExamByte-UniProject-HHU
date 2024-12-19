package org.example.exambyte.webconroller;

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
