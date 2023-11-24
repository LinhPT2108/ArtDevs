package com.art.controller.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Testh {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123123a"));
	}
}
