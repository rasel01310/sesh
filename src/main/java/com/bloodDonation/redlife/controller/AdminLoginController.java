package com.bloodDonation.redlife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
    @GetMapping("/admin-login")
    public String showAdminLogin(Model model) {
        model.addAttribute("isAdmin", true);
        return "login";
    }
} 