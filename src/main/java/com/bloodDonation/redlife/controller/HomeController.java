package com.bloodDonation.redlife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/user-login")
    public String userLogin(Model model) {
        model.addAttribute("isAdmin", false);
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("isAdmin", false);
        return "login";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
} 