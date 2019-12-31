package com.hung.test.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hung.config.jwt.JwtTokenProvider;
import com.hung.config.security.CustomUserDetails;
import com.hung.controller.AuthController;
import com.hung.dto.payload.LoginRequest;
import com.hung.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthController.class, secure = false)
public class AuthTest {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	JwtTokenProvider tokenPrivider;
	
	@MockBean
	UserService userService;
	
	@MockBean
	AuthenticationManager authenticationManager;

	@Test
	public void login() throws Exception {
		String token = "token";
		String name = "hung";
		String password = "123456";
		Authentication authentication = null;
		LoginRequest loginRequest = new LoginRequest(name, password);
		UserDetails userDetails = null;
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(name, password);
		
		given(authenticationManager.authenticate(user)).willReturn(authentication);
		given(userService.loadUserByUsername(name)).willReturn(userDetails);
		userDetails = userService.loadUserByUsername(name); 
		given(tokenPrivider.generateToken((CustomUserDetails)userDetails)).willReturn(token);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/login")
				.content(asJsonString( loginRequest)) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken", is(token)));
	}	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
