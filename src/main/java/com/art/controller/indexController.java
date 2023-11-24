package com.art.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping(value = {"/", ""})
    public String redirectArtDevs() {
        return "redirect:/ArtDevs";
    }

    @GetMapping(value = "/ArtDevs")
    public String getArtDevs() {
        return "index";
    }

    @GetMapping(value = "/about-us")
    public String getAboutUs() {
        return "index";
    }
}
