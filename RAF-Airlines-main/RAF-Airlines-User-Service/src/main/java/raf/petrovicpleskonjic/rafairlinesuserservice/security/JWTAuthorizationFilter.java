package raf.petrovicpleskonjic.rafairlinesuserservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.AdministratorRepository;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.UserRepository;

import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepo;
	private AdministratorRepository adminRepo;

	@Autowired
	public JWTAuthorizationFilter(AuthenticationManager authManager, UserRepository userRepo, AdministratorRepository adminRepo) {
		super(authManager);
		this.userRepo = userRepo;
		this.adminRepo = adminRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String token = req.getHeader(HEADER_STRING);

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req, token);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {

		if (token != null) {
			DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, ""));

			String username = jwt.getSubject();
			if (username == null)
				return null;

			if (userRepo.existsByEmail(username))
				return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());	
			else if (adminRepo.existsByUsername(username)) {
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
				return new UsernamePasswordAuthenticationToken(username, null, authorities);
			}
				
			return null;
		}
		return null;
	}
}
