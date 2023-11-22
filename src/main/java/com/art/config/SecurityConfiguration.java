package com.art.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests()
//                .requestMatchers("/account/login", "/account/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/account/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//                .build();
//    }
}