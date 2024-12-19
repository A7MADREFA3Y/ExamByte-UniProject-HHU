package org.example.exambyte.webconroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/KorrektorDashBoard")
public class KorrektorController {

    @GetMapping("/")
    public String DashBoardKorrektor() {
        return "KorrektorTemp/korrektorDash";
    }
}
