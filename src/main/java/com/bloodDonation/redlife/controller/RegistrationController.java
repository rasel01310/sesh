package com.bloodDonation.redlife.controller;

import com.bloodDonation.redlife.entity.User;
import com.bloodDonation.redlife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().matches("01[0-9]{9}")) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Please enter a valid Bangladeshi phone number (11 digits, starts with 01)");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/user-login";
    }
} 