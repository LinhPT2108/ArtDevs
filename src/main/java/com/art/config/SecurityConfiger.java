package com.art.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.art.config.auth.JwtAuthenticationFilter;
import com.art.utils.Path;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiger {

	private final AuthenticationProvider authenticationProvider;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	/*
	 * config đường dẫn và giao thức đăng nhập, đăng xuất,
	 * BASE_PATH = "/rest"
	 * ADMIN_PATH = "/admin"
	 * đăng nhập với Email và Password
	 * Pass word : 123123a -> đã được mã hóa thành "$2a$10$kpnU5NRvBiGYfLoH.GuQ5uUFHx6M37QuihnsfN1z60VqCzX24HFZK"
	 * Cập nhật password đã được mã hóa vào csdl
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests((authz) -> authz
	        		.requestMatchers(
	        				AntPathRequestMatcher.antMatcher("/*"),
	        				AntPathRequestMatcher.antMatcher("/account/login"),
	        				AntPathRequestMatcher.antMatcher("/product"),
	        				AntPathRequestMatcher.antMatcher("/product/**"),
	        				AntPathRequestMatcher.antMatcher("/api/login")
	        				).permitAll()
	        		.requestMatchers(
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/cart"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/cart/**")
	        				).hasAnyAuthority("user")
	        		.requestMatchers(
							AntPathRequestMatcher.antMatcher("/account/**"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/account"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/account/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/account"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/account/**")
	        				).hasAnyAuthority("admin", "staff", "shipper","user")
	        		.requestMatchers(
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/order"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/order/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/order"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/order/**")
	        				).hasAnyAuthority("admin", "staff", "shipper")
	        		.requestMatchers(
	        				AntPathRequestMatcher.antMatcher("/admin/dashboard"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/product"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/product/**"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/flashSale"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/flashSale/**"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/voucher"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/voucher/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/manufacturer"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/manufacturer/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/category"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/category/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/product"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/product/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/banner"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/banner/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/flashSale"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/flashSale/**"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/voucher"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/voucher/**"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/update-status")
	        				).hasAnyAuthority("admin", "staff")
	        		.requestMatchers(
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/userCustom"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/userCustom/**"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/statistical-revenue"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/statistical-revenue/**"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/statistical-order"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/statistical-wishlist"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/statistical-orders-by-user"),
	        				AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/statistical-best-seller"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/userCustom"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/userCustom/**"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/statistical-revenue"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/statistical-revenue/**"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/statistical-order"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/statistical-wishlist"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/statistical-orders-by-user"),
	        				AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/statistical-best-seller")
	        				).hasAnyAuthority("admin")
	        		.requestMatchers(AntPathRequestMatcher.antMatcher("/demo/access")).hasAnyAuthority( "shipper","admin","user","staff")
	                .anyRequest().permitAll()
	        		)
	        
//	        .formLogin(login -> login
//	                .loginPage("/account/login")
//	                .defaultSuccessUrl("/", false)
//	                .permitAll()
//	                )
	        .formLogin(login -> login
	                .loginPage("/account/login")
	                .loginProcessingUrl("/account/login")
	                .usernameParameter("email")
	                .passwordParameter("password")
	                .defaultSuccessUrl("/", false))
	        .logout(logout -> logout
	                .logoutUrl("/account/logout")
	                .logoutSuccessUrl("/account/login")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID")
	                );
	        
//	        .authenticationProvider(authenticationProvider)
//	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);	
	    return http.build();
	}
	
	/*
	 * Config cho phép truy cập vào tất cả các file trong pattern của antMatcher
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(
				AntPathRequestMatcher.antMatcher("/static/**"),
				AntPathRequestMatcher.antMatcher("/templates/**"));
	}
}