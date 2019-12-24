
package com.hung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hung.dto.UserDTO;
import com.hung.exceptions.UserExistionException;
import com.hung.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping()
	public List<UserDTO> getAll() {
		return userService.findAll();
	}

	@GetMapping(value = "/{id}")
	public UserDTO getOneById(@PathVariable long id) {
		return userService.findById(id);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.delete(id);
	}

	@PostMapping()
	public UserDTO insertUser(@RequestBody UserDTO user) {
		if (userService.hadUser(user)) {
			throw new UserExistionException();
		}
		return userService.save(user);
	}

	@PutMapping(value = "/{id}")
	public UserDTO editUser(@RequestBody UserDTO user, @PathVariable long id) {
		user.setId(id);
		return userService.save(user);
	}

}
