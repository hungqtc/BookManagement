
package com.hung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hung.dto.RoleDTO;
import com.hung.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	RoleService roleService;

	@GetMapping(value = "/role")
	public List<RoleDTO> getAll() {
		
		return roleService.getAll();
		
	}
	
	@GetMapping(value = "/role/{id}")
	public RoleDTO getOneById(@PathVariable long id) {
		return roleService.getById(id);
	}
	
	@DeleteMapping(value = "/role/{id}")
	public void deleteRole(@PathVariable long id) {
		roleService.delete(id);
	}

	@PostMapping(value = "/role")
	public RoleDTO insertRole(@RequestBody RoleDTO role) {
		return roleService.save(role);
	}

	@PutMapping(value = "/role/{id}")
	public RoleDTO editRole(@RequestBody RoleDTO role, @PathVariable long id) {
		role.setId(id);
		return roleService.save(role);
	}

}
