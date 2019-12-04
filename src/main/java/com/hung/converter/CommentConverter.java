package com.hung.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hung.dto.AddressDTO;
import com.hung.entity.CommentEntity;

@Component
public class CommentConverter {
	public CommentEntity toEntity(AddressDTO dto) {
		CommentEntity entity = new CommentEntity();
		return entity;
	}

	public static AddressDTO toDTO(CommentEntity entity) {
		AddressDTO dto = new AddressDTO();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		return dto;
	}

	public CommentEntity toEntity(AddressDTO dto, CommentEntity entity) {
		return entity;
	}
	
	public List<AddressDTO> toDTO(List<CommentEntity> listEntity) {
		List<AddressDTO> listDTO = new ArrayList<AddressDTO>();
		for (CommentEntity entity : listEntity) {
			listDTO.add(toDTO(entity));
		}
		return listDTO;
				
	}

}
