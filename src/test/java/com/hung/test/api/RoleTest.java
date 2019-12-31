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
import com.hung.controller.RoleController;
import com.hung.dto.RoleDTO;
import com.hung.service.RoleService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RoleController.class, secure = false)
public class RoleTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	RoleService roleService;

	@Test
	public void getRole() throws Exception {
		List<String> users = new ArrayList<>();
		users.add("hung");
		RoleDTO admin = new RoleDTO("admin", users);
		RoleDTO user = new RoleDTO("user", users); 
		List<RoleDTO> listRole = Arrays.asList(admin, user);

		given(roleService.findAll()).willReturn(listRole);
		mvc.perform(get("/api/roles").contentType(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is(listRole.get(0).getName())))
				.andExpect(jsonPath("$[0].users", is(listRole.get(0).getUsers())));
	}

	@Test
	public void getRoleById() throws Exception {
		List<String> users = new ArrayList<>();
		users.add("hung");
		RoleDTO admin = new RoleDTO("admin", users);
		long id = 1;
		
		given(roleService.findById(id)).willReturn(admin);
		mvc.perform(get("/api/roles/{id}", id).contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(admin.getName())))
				.andExpect(jsonPath("$.users", is(admin.getUsers())));
	}

	@Test
	public void insertRole() throws Exception {
		List<String> users = new ArrayList<>();
		users.add("hung");
		RoleDTO admin = new RoleDTO("admin", users);
		
		given(roleService.save(admin)).willReturn(admin);
		mvc.perform(MockMvcRequestBuilders.post("/api/roles")
			    .content(asJsonString(admin)) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(admin.getName())));
	}
	
	@Test
	public void editRole() throws Exception {
		List<String> users = new ArrayList<>();
		users.add("hung");
		long id = 1;
		RoleDTO admin = new RoleDTO("admin", users);
		admin.setId(id);
		
		given(roleService.save(admin)).willReturn(admin);
		mvc.perform(MockMvcRequestBuilders.put("/api/roles/{id}", id)
			    .content(asJsonString(admin)) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(admin.getName())));
	}
	
	@Test
	public void deleteRole() throws Exception 
	{
	  mvc.perform( MockMvcRequestBuilders.delete("/api/roles/{id}", 1))
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
