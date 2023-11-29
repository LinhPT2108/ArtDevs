package com.art.service.auth;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.art.models.user.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private static final String secret_key = "123";

	public String generateToken(Account user, Collection<SimpleGrantedAuthority> authorities) {
		Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
		return JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 50 * 60 * 1000))
				.withClaim("roles",
						authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
	}

	public String generateRefeshToken(Account user, Collection<SimpleGrantedAuthority> authorities) {
		Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
		return JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 80 * 60 * 1000)).sign(algorithm);
	}

	public boolean validateToken(String authToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_key.getBytes());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject("art-backend")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(authToken);

            Date expirationDate = decodedJWT.getExpiresAt();
            Instant expirationInstant = expirationDate.toInstant();
            LocalDate expirationLocalDate = LocalDate.ofInstant(expirationInstant, ZoneId.systemDefault());
            if (expirationLocalDate.isBefore(LocalDate.now())) {
                System.out.println("Expired JWT token");
                return false;
            }

            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT token signature");
        } catch (JWTVerificationException ex) {
            System.out.println("Invalid JWT token");
        }
        return false;
    }

}
