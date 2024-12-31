package org.example.exambyte.webConroller;

import org.example.exambyte.service.ServiceImp;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webConroll {



    //only normal landing page to login,
    // then every role will be redirected to a different controller

    @GetMapping("/")
    public String landingPageForAll() {

        return "LandingPage";
    }

    @GetMapping("/redirctingWithUser")
    public String redirctingWithUsers(Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                grantedAuthority
                .getAuthority()
                .equals("ROLE_ADMIN"))){
            return "redirect:/adminDashBoard/";

        }else if (auth.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                grantedAuthority.getAuthority()
                .equals("ROLE_USER"))){
            return "redirect:/userDashBoard/";

        } else if (auth.getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                grantedAuthority
                .getAuthority()
                .equals("ROLE_CORRECTOR"))) {
            return "redirect:/correctorDashBoard/";
        }
        return "error";
    }



}