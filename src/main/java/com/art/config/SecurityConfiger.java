package com.art.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.art.dao.user.AccountDAO;
import com.art.dao.user.AccountRoleDAO;
import com.art.models.user.Account;
import com.art.models.user.AccountRole;
import com.art.models.user.Role;
import com.art.service.user.CustomUserDetailService;
import com.art.utils.Path;
import com.art.utils.Permission;

import jakarta.servlet.RequestDispatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiger {

	@Autowired
	private AccountDAO aDAO;
	
	@Autowired
	private AccountRoleDAO arDAO;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	
	/*
	 * Mã hóa mật khẩu
	 */
	@Autowired
	public void config(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailService)
	                .passwordEncoder(new BCryptPasswordEncoder());

		// set cứng data
//		auth.inMemoryAuthentication().withUser("admin").password(new BCryptPasswordEncoder().encode("123456"))
//				// .roles("ADMIN") // ROLE_ADMIN
//				.authorities("admin").and().passwordEncoder(new BCryptPasswordEncoder());

	}
	
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
	        				AntPathRequestMatcher.antMatcher("/account/verify-code/**"),
	        				AntPathRequestMatcher.antMatcher("/product"),
	        				AntPathRequestMatcher.antMatcher("/product/**")
	        				).permitAll()
	        		.requestMatchers(
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/cart"),
							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/cart/**")
	        				).hasAnyAuthority("user")
	        		.requestMatchers(
							AntPathRequestMatcher.antMatcher("/account/profile")
//							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/account"),
//							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/account/**"),
//							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/account"),
//							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/account/**")
	        				).hasAnyAuthority("admin", "staff", "shipper","user")
	        		.requestMatchers(
							// AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/order"),
							// AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/order/**"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/order"),
							AntPathRequestMatcher.antMatcher(Path.ADMIN_PATH + "/order/**")
	        				).hasAnyAuthority("admin", "staff", "shipper")
	        		.requestMatchers(
	        				AntPathRequestMatcher.antMatcher("/admin/dashboard"),
//							AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/product"),
							// AntPathRequestMatcher.antMatcher(Path.BASE_PATH + "/product/**"),
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
	                .anyRequest().permitAll()
	        		)
	        .formLogin(login -> login
	                .loginPage("/account/login")
	                .loginProcessingUrl(Path.BASE_PATH+"/account/user")
	                .usernameParameter("email")
	                .passwordParameter("password")
	                .defaultSuccessUrl("/", false))
	        .logout(logout -> logout
	                .logoutUrl("/account/logout")
	                .logoutSuccessUrl("/account/login")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID"))
	        
	        .oauth2Login(oauth2 -> oauth2
				    .loginPage("/account/login")
				    .authorizationEndpoint(authorization -> authorization
				        .baseUri("/login/oauth2/authorization")
				        .authorizationRequestRepository(getrRepository())
				    )
				    .defaultSuccessUrl("/ArtDev",false)
				    .userInfoEndpoint(o -> o.oidcUserService(oidcUserService()))
		        .successHandler((req, res, user) -> {
					if (user instanceof OAuth2AuthenticationToken) {
						OAuth2AuthenticationToken oAuthenticationToken = (OAuth2AuthenticationToken) user;
						String username = oAuthenticationToken.getPrincipal().getName();
						String email = oAuthenticationToken.getPrincipal().getAttribute("email");
						String fullname = oAuthenticationToken.getPrincipal().getAttribute("name");
						if (!isUserExist(email)) {
							System.out.println(email + " 123123");

							System.out.println(fullname + " fullname");
							System.out.println(username + " username");
							Account newUser = createUser(username, email, fullname);
							grantPermissions(newUser, new HashSet<>(Set.of(Permission.user)));
							RequestDispatcher dispatcher = req.getRequestDispatcher("http://localhost:8080/login/oauth2/authorization/google");
							dispatcher.forward(req, res);
							return;
						}
					}
				})
		        )
	        ;
				
	        
	    return http.build();
	}
	
	/*
	 * Config cho phép truy cập vào tất cả các file trong pattern của antMatcher
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"));
	}
	
	
	public boolean isUserExist(String email) {
		  Account user = aDAO.findByEmail(email);
		  return user!=null;
		}
	
	public Account createUser(String uersname,String email, String name) {
		Account user = new Account();
		user.setAccountId(uersname);
		user.setEmail(email);
		user.setFullname(name);
		user.setPassword(new BCryptPasswordEncoder().encode(uersname));
		user.setStatus(false);
		aDAO.save(user);
		return user;
	}
	
	public void grantPermissions(Account user, Set<Permission> permissions) {
		Role role = new Role(2, "user");
		List<AccountRole> listACRole = new ArrayList<>();
		AccountRole acrole = new AccountRole(user, role);
		listACRole.add(acrole);
		user.setUserRole(listACRole);
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (AccountRole accountRole : listACRole) {
			authorities.add(new SimpleGrantedAuthority(accountRole.getRole().getRoleName()));
		}
		aDAO.save(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null,authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	@Bean
	AuthorizationRequestRepository<OAuth2AuthorizationRequest> getrRepository(){
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
	
	@Bean
	OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken(){
		return new DefaultAuthorizationCodeTokenResponseClient();
	}
	
	@Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                OidcUser user = super.loadUser(userRequest);
                // You can customize the user details if needed
                return user;
            }
        };
    }
}