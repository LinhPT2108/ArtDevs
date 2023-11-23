package com.art.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.art.service.user.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiger {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
//	@Bean
//	BCryptPasswordEncoder getBCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Autowired
	public void config(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailService)
	                .passwordEncoder(new BCryptPasswordEncoder());

		// set cứng data
//		auth.inMemoryAuthentication().withUser("admin").password(new BCryptPasswordEncoder().encode("123456"))
//				// .roles("ADMIN") // ROLE_ADMIN
//				.authorities("admin").and().passwordEncoder(new BCryptPasswordEncoder());

	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests((authz) -> authz
	        		.requestMatchers(AntPathRequestMatcher.antMatcher("/*")).permitAll()
	        		.requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasAnyAuthority("admin")
	                .anyRequest().permitAll()
	        		)
	        .formLogin(login -> login
	                .loginPage("/login")
	                .loginProcessingUrl("/login")
	                .usernameParameter("email")
	                .passwordParameter("password")
	                .defaultSuccessUrl("/admin/dashboard", true))
	        .logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID"));	
	    return http.build();
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"));
	}
}