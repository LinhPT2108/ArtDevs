package com.art.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping(value = { "/", "", "/ArtDevs" })
    public String getArtDevs() {
        return "index";
    }
}
