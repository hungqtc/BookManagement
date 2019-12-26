package com.hung.service;

import java.util.List;
import com.hung.dto.RoleDTO;

public interface RoleService {
	List<RoleDTO> findAll();

	RoleDTO findById(Long id);

	RoleDTO save(RoleDTO roleDTO);

	void delete(Long roleId);
}
