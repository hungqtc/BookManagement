
package com.hung.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.hung.dto.BookDTO;
import com.hung.dto.output.BookOutput;

public interface BookService {

	BookOutput findAll();

	BookOutput findAll(Pageable pageable);

	List<BookDTO> findBySearch(String search, Pageable pageable);

	BookOutput findAllEnableSearch(String search, Pageable pageable);

	BookDTO findById(long id);

	BookDTO save(BookDTO BookDTO);

	void delete(long[] ids);

}
