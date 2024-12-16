package org.example.exambyte.webconroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webconroll {


    //only normal landing page to login,
    // then every role will be redirected to a different controller

    @GetMapping("/")
    public String landingPageForAll() {
        return "LandingPage";
    }

}