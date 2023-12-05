package com.art.service.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.art.config.User.CustomUserDetails;
import com.art.models.user.Account;
import com.art.models.user.AccountRole;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private AccountServiceImpl accountService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountService.findByEmail(email);
		if (account == null) {
			System.out.println("co vô không");
			throw new UsernameNotFoundException("Not found !");
		}

		System.out.println("Email: " + account.getEmail());
		Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
		List<AccountRole> roles = account.getUserRole();
		for (AccountRole accountRole : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(accountRole.getRole().getRoleName()));
		}
		return new CustomUserDetails(account, grantedAuthorities);
	}

//	public void loginFormOAuth2(OAuth2AuthenticationToken oauth2) {
//		String email = oauth2.getPrincipal().getAttribute("email");
//		String password = Long.toHexString(System.currentTimeMillis());
//		UserDetails user = User.withUsername(email).password(new BCryptPasswordEncoder().encode(password)).roles("user")
//				.build();
//		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(auth);
//	}
}
