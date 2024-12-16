package org.example.exambyte.webconroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webconroll {

    @GetMapping("/")
    public String landingPageForAll() {
        return "LandingPage";
    }

}