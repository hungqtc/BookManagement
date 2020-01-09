
package com.hung.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hung.config.jwt.JwtTokenProvider;
import com.hung.config.security.CustomUserDetails;
import com.hung.dto.payload.LoginRequest;
import com.hung.dto.payload.LoginResponse;
import com.hung.service.UserService;

@RestController
public class AuthController {

	@Autowired(required=false)
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@PostMapping("/api/login")
	public LoginResponse createAuthenticationToken(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

		Authentication authentication = authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
		
		final String jwt = tokenProvider.generateToken((CustomUserDetails) userDetails);

		return new LoginResponse(jwt);
	}
}
