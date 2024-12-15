package org.example.exambyte.webconroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("SUI")
public class webconroll {

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
