
package com.hung.service.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hung.converter.BookConverter;
import com.hung.dto.BookDTO;
import com.hung.entity.BookEntity;
import com.hung.repository.BookRepository;
import com.hung.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookConverter bookConverter;

	@Override
	public List<BookDTO> findAll() {
		List<BookEntity> listEntity = bookRepository.findAll();
		return bookConverter.toDTO(listEntity);
	}

	@Override
	public BookDTO getById(long id) {
		BookEntity entity = bookRepository.findOne(id);
		return bookConverter.toDTO(entity);
	}

	@Override
	public BookDTO save(BookDTO BookDTO) {
		BookEntity BookEntity = new BookEntity();

		if (BookDTO.getId() != null) {
			BookEntity oldBookEntity = bookRepository.findOne(BookDTO.getId());
			BookEntity = bookConverter.toEntity(BookDTO, oldBookEntity);
		} else {
			BookEntity = bookConverter.toEntity(BookDTO);
		}
		BookEntity = bookRepository.save(BookEntity);
		return bookConverter.toDTO(BookEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			//bookRepository.findOne(item);
			
			bookRepository.delete(item);
		}
	}

	@Override
	public List<BookDTO> findAll(Pageable pageable) {
		List<BookEntity> entities = bookRepository.findAll(pageable).getContent();
		return bookConverter.toDTO(entities);
	}

	@Override
	public int totalItem() {
		return (int) bookRepository.count();
	}

	@Override
	public List<BookDTO> findAllByUserCreated(String userName, Pageable pageable) {
		List<BookEntity> listEntity = bookRepository.findByCreatedBy(userName, pageable);
		System.out.println(listEntity.size());
		return bookConverter.toDTO(listEntity);
	}

	@Override
	public BookDTO findByTitle(String title ) {
		return bookConverter.toDTO(bookRepository.findByTitle(title));
	}

	@Override
	public List<BookDTO> findAllEnable(Pageable pageable) {
		return bookConverter.toDTO(bookRepository.findByEnabled(1, pageable));
	}

	@Override
	public int totalItemUserCreate(String userName) {
		return bookRepository.count(userName);
	}

	@Override
	public int totalItemStatus(int status) {
		return bookRepository.count(status);
	}

}
