package com.hung.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hung.dto.UserDTO;

public interface UserService  extends UserDetailsService  {
	List<UserDTO> getAll();
	
	boolean hadUser(UserDTO userDTO); 

	UserDTO getById(Long id);

	UserDTO save(UserDTO userDTO);
	
	UserDetails loadUserById(Long id);

	void delete(Long userId);
}
