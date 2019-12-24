
package com.hung.service.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hung.config.security.CustomUserDetails;
import com.hung.constants.CommonConstant;
import com.hung.converter.BookConverter;
import com.hung.dto.BookDTO;
import com.hung.dto.output.BookOutput;
import com.hung.entity.BookEntity;
import com.hung.exceptions.BookExistionException;
import com.hung.exceptions.UnauthorizedException;
import com.hung.repository.BookRepository;
import com.hung.repository.UserRepository;
import com.hung.service.BookService;
import com.hung.service.CommentService;
import com.hung.utils.PageUtil;
import com.hung.utils.SecurityUtil;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookConverter bookConverter;

	@Autowired(required = false)
	CommentService commentService;

	@Autowired
	private UserRepository userRepository;

	private CustomUserDetails userLogin;

	private String userRoles;

	private BookOutput bookOutput;

	private void checkLogin() {
		if (SecurityUtil.getPrincipal() != null) {
			userLogin = SecurityUtil.getPrincipal();
			userRoles = SecurityUtil.checkRoleUser(userLogin.getAuthorities().toString());
		}
	}

	@Override
	public BookOutput findAll() {
		bookOutput = new BookOutput();
		List<BookEntity> listEntity = bookRepository.findAll();

		bookOutput.setPage(1);
		bookOutput.setTotalPage(1);
		bookOutput.setListResult(bookConverter.toDTO(listEntity));
		return bookOutput;
	}

	@Override
	public BookDTO findById(long id) {
		BookEntity entity = bookRepository.findById(id).get();
		return bookConverter.toDTO(entity);
	}

	@Override
	public BookDTO save(BookDTO bookDTO) {
		if (bookRepository.findByTitle(bookDTO.getTitle()) != null) {
			throw new BookExistionException();
		}
		checkLogin();
		BookEntity bookEntity = new BookEntity();

		if (bookDTO.getId() != null) {
			BookEntity oldBookEntity = bookRepository.findById(bookDTO.getId()).get();
			if (("ADMIN").equals(userRoles) || userLogin.getUsername().equals(oldBookEntity.getCreatedBy())) {
				bookEntity = bookConverter.toEntity(bookDTO, oldBookEntity);
			} else {
				throw new UnauthorizedException();
			}
		} else {
			bookEntity = bookConverter.toEntity(bookDTO);
			String email = userLogin.getUsername();
			bookEntity.setUser(userRepository.findByEmail(email));
		}

		bookEntity = bookRepository.save(bookEntity);
		return bookConverter.toDTO(bookEntity);
	}

	@Override
	public void delete(long[] ids) {
		checkLogin();
		for (Long id : ids) {
			BookEntity book = bookRepository.findById(id).get();
			if (!userLogin.getUsername().equals(book.getCreatedBy())) {
				throw new UnauthorizedException();
			}
		}
		// delete all comment by idBook
		commentService.deleteByBook(ids);
		for (long id : ids) {
			bookRepository.deleteById(id);
		}
	}

	@Override
	public BookOutput findAll(Pageable pageable) {
		checkLogin();
		List<BookEntity> listBookResult = new ArrayList<>();
		int totalItem = 0;
		// check userRole
		if (("ADMIN").equals(userRoles)) {
			listBookResult = bookRepository.findAll(pageable).getContent();
			totalItem = (int) bookRepository.count();

		} else if (("USER").equals(userRoles)) {
			String userName = userLogin.getUsername();
			totalItem = bookRepository.count(userName);
			listBookResult = bookRepository.findByCreatedBy(userName, pageable);
		} else {
			totalItem = bookRepository.count(CommonConstant.STATUS_ENABLE);
			listBookResult = bookRepository.findByStatus(CommonConstant.STATUS_ENABLE, pageable);
		}

		bookOutput.setTotalPage(PageUtil.totalPage(totalItem, pageable.getPageSize()));
		bookOutput.setPage(pageable.getPageNumber() + 1);
		bookOutput.setListResult(bookConverter.toDTO(listBookResult));

		return bookOutput;
	}

	@Override
	public List<BookDTO> findBySearch(String search, Pageable pageable) {
		List<BookEntity> listEntity = bookRepository.findByTitleOrAuthor(search, search, pageable);
		return bookConverter.toDTO(listEntity);

	}

	@Override
	public BookOutput findAllEnableSearch(String search, Pageable pageable) {
		bookOutput = new BookOutput();
		search = "%" + search + "%";
		int totalItem = bookRepository.countByTitleOrAuthor(search, search, CommonConstant.STATUS_ENABLE);

		bookOutput.setTotalPage(PageUtil.totalPage(totalItem, pageable.getPageSize()));
		bookOutput.setPage(pageable.getPageNumber() + 1);
		List<BookEntity> listEntity = bookRepository.findByTitleOrAuthor(search, search, CommonConstant.STATUS_ENABLE,
				pageable);
		bookOutput.setListResult(bookConverter.toDTO(listEntity));
		return bookOutput;
	}

}
