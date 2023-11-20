package com.art.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping(value = "/products")
public class productSiteController {
    @GetMapping(value="")
    public String getProductSite() {
        return "index";
    }
    
}
