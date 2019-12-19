package com.hung.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.hung.dto.CommentDTO;

public interface CommentService {
	List<CommentDTO> findAll();
	
	List<CommentDTO> findAll(Pageable pageable);
	
	CommentDTO findById(Long id);

	CommentDTO save(CommentDTO commentDTO);

	void delete(long[] ids);
	
	void delete(long userId);
	
	void deleteByBook(long[] ids);
	
	int totalItem();
	
	
}
