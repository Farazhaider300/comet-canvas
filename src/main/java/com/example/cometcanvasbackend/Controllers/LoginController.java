package com.example.cometcanvasbackend.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/custom-login")
    public String showLoginForm() {
        return "Log-in";
    }
}

