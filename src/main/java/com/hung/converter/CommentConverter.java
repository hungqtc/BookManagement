package com.hung.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.hung.dto.CommentDTO;
import com.hung.entity.CommentEntity;

@Component
public class CommentConverter {
	public CommentEntity toEntity(CommentDTO dto) {
		CommentEntity entity = new CommentEntity();
		entity.setMessage(dto.getMessage());
		return entity;
	}

	public static CommentDTO toDTO(CommentEntity entity) {
		CommentDTO dto = new CommentDTO();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setMessage(entity.getMessage());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		return dto;
	}

	public CommentEntity toEntity(CommentDTO dto, CommentEntity entity) {
		entity.setMessage(dto.getMessage());
		return entity;
	}
	
	public List<CommentDTO> toDTO(List<CommentEntity> listEntity) {
		List<CommentDTO> listDTO = new ArrayList<CommentDTO>();
		for (CommentEntity entity : listEntity) {
			listDTO.add(toDTO(entity));
		}
		return listDTO;
	}
}
