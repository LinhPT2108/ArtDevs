package com.art;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class ArtDevsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtDevsApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder brBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}	
