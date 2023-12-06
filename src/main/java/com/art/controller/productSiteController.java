package com.art.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/products")
public class productSiteController {
	
	@GetMapping(value = "")
	public String getProductSite() {
		return "index";
	}

	@GetMapping(value = "/search")
	public String getProductSiteSearch() {
		return "index";
	}

	@GetMapping(value = "/{productId}")
	public String getProductDetail(@PathVariable("productId") String productId) {
		return "index";
	}
}
