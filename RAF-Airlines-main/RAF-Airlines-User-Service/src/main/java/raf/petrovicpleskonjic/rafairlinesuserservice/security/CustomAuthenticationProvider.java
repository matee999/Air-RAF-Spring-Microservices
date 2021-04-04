package raf.petrovicpleskonjic.rafairlinesuserservice.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import raf.petrovicpleskonjic.rafairlinesuserservice.models.Administrator;
import raf.petrovicpleskonjic.rafairlinesuserservice.models.User;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.AdministratorRepository;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private PasswordEncoder encoder;
	
	private UserRepository userRepo;
	private AdministratorRepository adminRepo;

	@Autowired
	public CustomAuthenticationProvider(UserRepository userRepo, AdministratorRepository adminRepo) {
		this.userRepo = userRepo;
		this.adminRepo = adminRepo;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String username = auth.getName();
		String password = auth.getCredentials().toString();
		
		Administrator admin = adminRepo.findByUsername(username);
		if (admin != null) {
			if (password.equals(admin.getPassword()))
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
			else
				throw new BadCredentialsException("Authentication failed");
		}

		User user = userRepo.findByEmail(username);
		if (user != null) {
			if (encoder.matches(password, user.getPassword()))
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
			else
				throw new BadCredentialsException("Authentication failed");
		}

		throw new BadCredentialsException("Authentication failed");
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
}
