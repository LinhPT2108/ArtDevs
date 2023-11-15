package com.art.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(value = "/cart")
public class cartController {
    @GetMapping(value = "")
    public String getCart() {
        return "index";
    }

}
