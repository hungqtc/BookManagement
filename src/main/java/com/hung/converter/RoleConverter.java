package com.hung.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.hung.dto.RoleDTO;
import com.hung.entity.RoleEntity;
import com.hung.entity.UserEntity;

@Component
public class RoleConverter {
	public RoleEntity toEntity(RoleDTO dto) {
		RoleEntity entity = new RoleEntity();
		entity.setName(dto.getName());
		return entity;
	}

	public static RoleDTO toDTO(RoleEntity entity) {
		RoleDTO dto = new RoleDTO();
		ArrayList<String> listString = new ArrayList<String>();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setName(entity.getName());
		
		List<UserEntity> listUser = entity.getUsers();
		for (UserEntity userEntity : listUser) {
			listString.add(userEntity.getEmail());
		}
		dto.setUsers(listString);
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		return dto;
	}

	public RoleEntity toEntity(RoleDTO dto, RoleEntity entity) {
		entity.setName(dto.getName());
		return entity;
	}
	
	public List<RoleDTO> toDTO(List<RoleEntity> listEntity) {
		List<RoleDTO> listDTO = new ArrayList<RoleDTO>();
		for (RoleEntity entity : listEntity) {
			listDTO.add(toDTO(entity));
		}
		return listDTO;
				
	}

}
