package com.art.controller.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.art.config.User.CustomUserDetails;

@Controller
public class Test {

	@GetMapping("/login")
	public String getLogin(Model model) {
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user", user);
		return "login";
	}
}
