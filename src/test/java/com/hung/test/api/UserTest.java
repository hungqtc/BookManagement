package com.hung.test.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hung.controller.UserController;
import com.hung.dto.UserDTO;
import com.hung.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	UserService userService;

	@Test
	public void getUser() throws Exception {
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");

		UserDTO hung = new UserDTO("hung", null, 1, roles);
		UserDTO hoa = new UserDTO("hoa", null, 1, roles); 
		List<UserDTO> listUser = Arrays.asList(hung, hoa);

		given(userService.findAll()).willReturn(listUser);

		mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].email", is(listUser.get(0).getEmail())))
				.andExpect(jsonPath("$[0].roles", is(hung.getRoles())));
	}

	@Test
	public void getUserById() throws Exception {
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");
		long id = 1;
		UserDTO hung = new UserDTO("hung", null, 1, roles); 
		given(userService.findById(id)).willReturn(hung);

		mvc.perform(get("/api/users/{id}", id).contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(hung.getEmail())))
				.andExpect(jsonPath("$.roles", is(hung.getRoles())));
	}

	@Test
	public void insertUser() throws Exception {
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");
		UserDTO van = new UserDTO("van", "123456", 1, roles);
		given(userService.save(van)).willReturn(van);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/users")
				
			    .content(asJsonString(van)) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(van.getEmail())));

	}
	
	@Test
	public void editUser() throws Exception {
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");
		UserDTO van = new UserDTO("van", "123456", 1, roles);
		given(userService.save(van)).willReturn(van);
		
		mvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", 1)
				
			    .content(asJsonString(van)) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(van.getEmail())));

	}
	
	@Test
	public void deleteUser() throws Exception 
	{
	  mvc.perform( MockMvcRequestBuilders.delete("/api/users/{id}", 1))
	        .andExpect(status().isOk());
	}


	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
