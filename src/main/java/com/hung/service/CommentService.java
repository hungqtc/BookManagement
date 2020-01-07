package com.hung.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.hung.dto.CommentDTO;

public interface CommentService {
	List<CommentDTO> findAllByBook(long bookId);
	
	List<CommentDTO> findAll(Pageable pageable);
	
	CommentDTO findById(Long id);

	CommentDTO save(CommentDTO commentDTO, long bookId);

	void delete(long id);
	
	void deleteByUser(long userId);
	
	void deleteByBook(long[] ids);
	
	int totalItem();
}
