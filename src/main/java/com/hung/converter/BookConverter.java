package com.hung.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.hung.dto.BookDTO;
import com.hung.entity.BookEntity;
import com.hung.utils.StringUtil;

@Component
public class BookConverter {
	public static BookEntity toEntity(BookDTO dto) {
		BookEntity entity = new BookEntity();
		entity.setDescription(dto.getDescription());
		entity.setAuthor(dto.getAuthor());
		entity.setNoUtf8Author(StringUtil.covertToString(dto.getAuthor()));
		entity.setTitle(dto.getTitle());
		entity.setNoUtf8Title(StringUtil.covertToString(dto.getTitle()));
		entity.setStatus(dto.getStatus());
		return entity;
	}

	public static BookDTO toDTO(BookEntity entity) {
		BookDTO dto = new BookDTO();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setTitle(entity.getTitle());
		dto.setAuthor(entity.getAuthor());
		dto.setStatus(entity.getStatus());
		dto.setDescription(entity.getDescription());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		return dto;
	}

	public static BookEntity toEntity(BookDTO dto, BookEntity entity) {
		entity.setDescription(dto.getDescription());
		entity.setTitle(dto.getTitle());
		entity.setAuthor(dto.getAuthor());
		entity.setStatus(dto.getStatus());
		return entity;
	}
	
	public static List<BookDTO> toDTO(List<BookEntity> listEntity) {
		List<BookDTO> listDTO = new ArrayList<BookDTO>();
		for (BookEntity entity : listEntity) {
			listDTO.add(toDTO(entity));
		}
		return listDTO;
	}
}
