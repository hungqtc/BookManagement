
package com.hung.service;

import com.hung.dto.BookDTO;
import com.hung.dto.output.BookOutput;

public interface BookService {
	
	BookOutput findAll(Integer page, Integer limit, String sort, String order, String search, Integer status);

	BookDTO findById(long id);

	BookDTO save(BookDTO BookDTO);

	void delete(long[] ids);

}
