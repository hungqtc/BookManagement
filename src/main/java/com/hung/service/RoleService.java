package com.hung.service;

import java.util.List;

import com.hung.dto.RoleDTO;

public interface RoleService {
	List<RoleDTO> getAll();

	RoleDTO getById(Long id);

	RoleDTO save(RoleDTO roleDTO);

	void delete(Long roleId);
}
