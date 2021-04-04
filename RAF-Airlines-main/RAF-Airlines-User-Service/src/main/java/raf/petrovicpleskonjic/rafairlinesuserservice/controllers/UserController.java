package raf.petrovicpleskonjic.rafairlinesuserservice.controllers;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.HEADER_STRING;
import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.SECRET;
import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.TOKEN_EXPIRATION_TIME;
import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.TOKEN_PREFIX;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;

import raf.petrovicpleskonjic.rafairlinesuserservice.EmailService;
import raf.petrovicpleskonjic.rafairlinesuserservice.UtilityMethods;
import raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.user.RegistrationRequest;
import raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.user.UpdatePasswordRequest;
import raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.user.UpdateRequest;
import raf.petrovicpleskonjic.rafairlinesuserservice.models.Tier;
import raf.petrovicpleskonjic.rafairlinesuserservice.models.User;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.TierRepository;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.UserRepository;

@RestController
@RequestMapping("")
public class UserController {

	private EmailService emailService;
	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	private TierRepository tierRepo;

	@Autowired
	public UserController(EmailService emailService, BCryptPasswordEncoder encoder, UserRepository userRepo,
			TierRepository tierRepo) {
		this.emailService = emailService;
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.tierRepo = tierRepo;
	}

	@GetMapping("/admin-verification")
	public ResponseEntity<Boolean> getProfile(Authentication authentication) {
		boolean isAdmin = false;
		for (GrantedAuthority a : authentication.getAuthorities())
			if (a.getAuthority().equals("ROLE_ADMIN"))
				isAdmin = true;

		return new ResponseEntity<>(isAdmin, HttpStatus.ACCEPTED);

	}

	@GetMapping("/profile-by-id")
	public ResponseEntity<User> getUserById(Authentication authentication, @RequestParam long userId) {
		try {
			boolean isAdmin = false;
			for (GrantedAuthority a : authentication.getAuthorities())
				if (a.getAuthority().equals("ROLE_ADMIN"))
					isAdmin = true;

			if (!isAdmin)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Optional<User> user = userRepo.findById(userId);
			if (!user.isPresent())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(user.get(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/my-profile")
	public ResponseEntity<User> getUserById(Authentication authentication) {
		try {
			User user = userRepo.findByEmail(authentication.getName());

			if (user == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {

		try {
			User user = new User(0, request.getName(), request.getSurname(), request.getEmail(),
					encoder.encode(request.getPassword()), request.getPassport());
			
			List<Tier> tiers = tierRepo.findAll();

			for (Tier tier : tiers) {
				if (tier.getThreshold() == 0) {
					user.setTier(tier);
					break;
				}
			}

			userRepo.saveAndFlush(user);

			StringBuilder body = new StringBuilder();
			body.append("Hello, ");
			body.append(request.getName());
			body.append("!\n\n");
			body.append("Thank you for joining RAF Airlines and we hope you'll have many wonderful"
					+ " flights with us!\n\nSincerely,\nRAF Airlines");

			emailService.sendSimpleMessage(request.getEmail(), "Welcome to RAF Airlines", body.toString());
			
			String token = JWT.create().withSubject(request.getEmail())
					.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
					.sign(HMAC512(SECRET.getBytes()));

			return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + token).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody UpdateRequest request,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			User user = userRepo.findByEmail(UtilityMethods.getUsernameFromToken(token));
			if (user == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			if (!user.getEmail().equals(request.getEmail())) {
				user.setEmail(request.getEmail());

				StringBuilder body = new StringBuilder();
				body.append("Hello, ");
				body.append(request.getName());
				body.append("!\n\n");
				body.append("We're sending this message to let you know that you have successfuly updated"
						+ " you e-mail address!\n\nSincerely,\nRAF Airlines");

				emailService.sendSimpleMessage(request.getEmail(), "RAF Airlines - Profile update", body.toString());
			}

			user.setName(request.getName());
			user.setSurname(request.getSurname());
			user.setPassport(request.getPassport());

			userRepo.save(user);

			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update-password")
	public ResponseEntity<User> update(@RequestBody UpdatePasswordRequest request,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			User user = userRepo.findByEmail(UtilityMethods.getUsernameFromToken(token));
			if (user == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			if (!encoder.matches(request.getCurrentPassword(), user.getPassword()))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			user.setPassword(encoder.encode(request.getNewPassword()));

			userRepo.save(user);

			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
