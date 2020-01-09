
package com.hung.service.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hung.converter.BookConverter;
import com.hung.dto.BookDTO;
import com.hung.dto.output.BookOutput;
import com.hung.entity.BookEntity;
import com.hung.exceptions.BookExistionException;
import com.hung.exceptions.StatusException;
import com.hung.exceptions.UnauthorizedException;
import com.hung.repository.BookRepository;
import com.hung.repository.UserRepository;
import com.hung.service.BookService;
import com.hung.service.CommentService;
import com.hung.utils.PageUtil;
import com.hung.utils.SecurityUtil;
import com.hung.utils.StringUtil;

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

	@Override
	public BookDTO findById(long id) {
		BookEntity entity = bookRepository.findById(id).get();
		return bookConverter.toDTO(entity);
	}

	@Override
	public BookDTO save(BookDTO bookDTO) {
		SecurityUtil.checkLogin();
		BookEntity bookEntity = new BookEntity();

		if (bookDTO.getId() != null) {
			BookEntity oldBookEntity = bookRepository.findById(bookDTO.getId()).get();
			if (("ADMIN").equals(SecurityUtil.userRoles) || SecurityUtil.userLogin.getUsername().equals(oldBookEntity.getCreatedBy())) {
				if (!bookDTO.getTitle().equals(oldBookEntity.getTitle())
						&& (bookRepository.findByTitle(bookDTO.getTitle()) != null)) {
					throw new BookExistionException();
				}
				bookEntity = bookConverter.toEntity(bookDTO, oldBookEntity);
			} else {
				throw new UnauthorizedException();
			}
		} else {
			if (bookRepository.findByTitle(bookDTO.getTitle()) != null) {
				throw new BookExistionException();
			}
			bookEntity = bookConverter.toEntity(bookDTO);
			String userName = SecurityUtil.userLogin.getUsername();
			bookEntity.setUser(userRepository.findByUserName(userName));
		}
		bookEntity = bookRepository.save(bookEntity);
		return bookConverter.toDTO(bookEntity);
	}

	@Override
	public void delete(long[] ids) {
		SecurityUtil.checkLogin();
		for (Long id : ids) {
			BookEntity book = bookRepository.findById(id).get();
			if (!SecurityUtil.userLogin.getUsername().equals(book.getCreatedBy())) {
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
	public BookOutput findAll(Integer page, Integer limit, String sort, String order, String search, Integer status) {
		if (status !=0  && status !=1 ) {
			throw new StatusException();
		}
		
		List<BookEntity> listBookResult = new ArrayList<>();
		int totalItem = 0;
		BookOutput bookOutput = new BookOutput();

		if (page != null && limit != null) {
			Sort objSort = new Sort(Sort.Direction.ASC, order);
			if ("DESC".equalsIgnoreCase(sort)) {
				objSort = new Sort(Direction.DESC, order);
			}
			Pageable pageable = PageRequest.of(page - 1, limit, objSort);

			SecurityUtil.checkLogin();
			if (search != null) {
				search = "%" + StringUtil.covertToString(search).toLowerCase() + "%"; 
				totalItem = bookRepository.countByTitleOrAuthor(search, search, status);
				listBookResult = bookRepository.findByTitleOrAuthor(search, search, status,
						pageable);
			}
			// check userRole
			else if (("ADMIN").equals(SecurityUtil.userRoles)) {
				listBookResult = bookRepository.findByStatus(status, pageable);
				totalItem = bookRepository.count(status);
			} else if (("USER").equals(SecurityUtil.userRoles)) {
				String userName = SecurityUtil.userLogin.getUsername();
				totalItem = bookRepository.count(userName);
				listBookResult = bookRepository.findByCreatedBy(userName, pageable);
			} else {
				status = 1;
				totalItem = bookRepository.count(status);
				listBookResult = bookRepository.findByStatus(status, pageable);
			}
			bookOutput.setPage(page);
			bookOutput.setTotalPage(PageUtil.totalPage(totalItem, limit));

		} else {
			listBookResult = bookRepository.findAll();
			bookOutput.setPage(1);
			bookOutput.setTotalPage(1);
		}
		bookOutput.setListResult(bookConverter.toDTO(listBookResult));
		return bookOutput;
	}
}
