
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
import com.hung.dto.RoleDTO;
import com.hung.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	RoleService roleService;

	@GetMapping
	public List<RoleDTO> getAll() {
		return roleService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public RoleDTO getOneById(@PathVariable long id) {
		return roleService.findById(id);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteRole(@PathVariable long id) {
		roleService.delete(id);
	}

	@PostMapping
	public RoleDTO insertRole(@RequestBody RoleDTO role) {
		return roleService.save(role);
	}

	@PutMapping(value = "/{id}")
	public RoleDTO editRole(@RequestBody RoleDTO role, @PathVariable long id) {
		role.setId(id);
		return roleService.save(role);
	}
}
