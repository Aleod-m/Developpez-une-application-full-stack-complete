package com.openclassrooms.mddapi.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.domain.UserDTO;
import com.openclassrooms.mddapi.domain.entity.User;
import com.openclassrooms.mddapi.domain.request.LoginRequest;
import com.openclassrooms.mddapi.domain.request.SignupRequest;
import com.openclassrooms.mddapi.domain.response.JwtResponse;
import com.openclassrooms.mddapi.domain.response.MessageResponse;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.UserMapper;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.config.jwt.JwtUtils;
import com.openclassrooms.mddapi.config.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbstractController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserService userSrvc;
	private final UserMapper userMapper;
	

	AuthController(AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder,
			JwtUtils jwtUtils,
			UserRepository userRepository, UserService userSrvc, UserMapper userMapper) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.userSrvc = userSrvc;
		this.userMapper = userMapper;
	}

	@PostMapping("login")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		boolean isAdmin = false;
		User user = this.userRepository.findById(userDetails.getId())
			.orElse(null);

		if (user != null) {
			isAdmin = user.isAdmin();
		}

		return ResponseEntity.ok(JwtResponse.builder()
			.token(jwt)
			.id(userDetails.getId())
			.username(userDetails.getUsername())
			.admin(isAdmin)
			.build());
	}

	@PostMapping("register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already taken!"));
		}
		if (userRepository.existsByUsername(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		// Create new user's account
		User user = User.builder()
			.email(signUpRequest.getEmail())
			.username(signUpRequest.getUserName())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.build();
		

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("me")
	public ResponseEntity<UserDTO> me() {
		return ResponseEntity.ok(userMapper.toDto(userSrvc.loggedInUser()));
	}
}
