package com.art.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.art.config.auth.AuthenticationRequest;
import com.art.config.auth.AuthenticationResponse;
import com.art.dao.user.AccountDAO;
import com.art.models.user.Account;
import com.art.models.user.AccountRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private final AccountDAO accountDAO;
	@Autowired
	JwtTokenProvider jwtService;
	@Autowired
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword() ));
		Account user = accountDAO.findByEmail(authenticationRequest.getEmail()).orElseThrow();
		List<AccountRole> accountRoles = null;
		if(user!=null) {
			accountRoles = accountDAO.getAccountRoles(user.getEmail());
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		List<AccountRole> sets = new ArrayList<>();
		accountRoles.stream().forEach(accountRole -> sets.add(new AccountRole(accountRole.getUser(), accountRole.getRole())));
		user.setUserRole(sets);
		sets.stream().forEach(set -> authorities.add(new SimpleGrantedAuthority(set.getRole().getRoleName())));
		var jwtToken = jwtService.generateToken(user, authorities);
		var jwtRefeshToken = jwtService.generateRefeshToken(user, authorities);
		return AuthenticationResponse.builder().token(jwtToken).refeshToken(jwtRefeshToken).build();
	}
}
