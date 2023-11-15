package com.art.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/account")
public class accountController {
    @GetMapping(value = "/profile")
    public String getProfile(Model model) {
        return "index";
    }

    @GetMapping(value = "/address")
    public String getAddress(Model model) {
        return "index";
    }

    @GetMapping(value = "/change-password")
    public String getChangePassword(Model model) {
        return "index";
    }

     @GetMapping(value = "/forgot-password")
    public String getForgotPassword(Model model) {
        return "index";
    }
} 
