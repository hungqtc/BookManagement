
package com.hung.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.hung.dto.BookDTO;

public interface BookService {

	List<BookDTO> findAll();
	
	List<BookDTO> findAllByUserCreated(String userName, Pageable pageable);
	
	List<BookDTO> findAll(Pageable pageable);
	
	List<BookDTO> findAllEnable(Pageable pageable);
	
	BookDTO findByTitle(String title);
	
	BookDTO getById(long id);

	BookDTO save(BookDTO BookDTO);

	void delete(long[] ids);

	int totalItem();
	
	int totalItemUserCreate(String userName);
	
	int totalItemStatus(int status);
	
}