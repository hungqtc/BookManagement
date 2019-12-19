
package com.hung.service.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hung.constants.CommonConstant;
import com.hung.converter.BookConverter;
import com.hung.dto.BookDTO;
import com.hung.entity.BookEntity;
import com.hung.repository.BookRepository;
import com.hung.repository.UserRepository;
import com.hung.service.BookService;
import com.hung.utils.SecurityUtil;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookConverter bookConverter;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<BookDTO> findAll() {
		List<BookEntity> listEntity = bookRepository.findAll();
		return bookConverter.toDTO(listEntity);
	}

	@Override
	public BookDTO getById(long id) {
		BookEntity entity = bookRepository.findById(id).get();
		return bookConverter.toDTO(entity);
	}

	@Override
	public BookDTO save(BookDTO BookDTO) {
		BookEntity bookEntity = new BookEntity();

		if (BookDTO.getId() != null) {
			BookEntity oldBookEntity = bookRepository.findById(BookDTO.getId()).get();
			bookEntity = bookConverter.toEntity(BookDTO, oldBookEntity);
		} else {
			bookEntity = bookConverter.toEntity(BookDTO);
			String userName = SecurityUtil.getPrincipal().getUsername();
			bookEntity.setUser(userRepository.findOneByName(userName));
		}

		bookEntity = bookRepository.save(bookEntity);
		return bookConverter.toDTO(bookEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long id : ids) {
			bookRepository.deleteById(id);
		}
	}

	@Override
	public void delete(long id) {
		bookRepository.deleteById(id);
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
		return bookConverter.toDTO(listEntity);
	}

	@Override
	public BookDTO findByTitle(String title) {
		return bookConverter.toDTO(bookRepository.findByTitle(title));
	}

	@Override
	public List<BookDTO> findAllEnable(Pageable pageable) {
		return bookConverter.toDTO(bookRepository.findByStatus(CommonConstant.ENABLE, pageable));
	}

	@Override
	public int totalItemUserCreate(String userName) {
		return bookRepository.count(userName);
	}

	@Override
	public int totalItemStatus(int status) {
		return bookRepository.count(status);
	}

	@Override
	public boolean hadBook(BookDTO book) {
		if (bookRepository.countByTitle(book.getTitle()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<BookDTO> findBySearch(String search, Pageable pageable) {
		List<BookEntity> listEntity = bookRepository.findByTitleOrAuthor(search, search, pageable);
		return bookConverter.toDTO(listEntity);

	}

	@Override
	public int totalItem(List<BookDTO> list) {
		return list.size();
	}

	@Override
	public List<BookDTO> findAllEnableSearch(String search, Pageable pageable) {

		List<BookEntity> listEntity = bookRepository.findByTitleOrAuthor(search, search, CommonConstant.ENABLE,
				pageable);

		return bookConverter.toDTO(listEntity);
	}

	

}
