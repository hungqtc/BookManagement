
package com.hung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hung.config.security.CustomUserDetails;
import com.hung.constants.CommonConstant;
import com.hung.dto.BookDTO;
import com.hung.handlers.exceptions.BookExistionException;
import com.hung.handlers.exceptions.UnauthorizedException;
import com.hung.dto.output.BookOutput;
import com.hung.service.BookService;
import com.hung.service.CommentService;
import com.hung.utils.PageUtil;
import com.hung.utils.SecurityUtil;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	CommentService commentService;

	private CustomUserDetails userLogin;

	private String userRoles;

	private void checkLogin() {
		if (SecurityUtil.getPrincipal() != null) {
			userLogin = SecurityUtil.getPrincipal();
			userRoles = SecurityUtil.checkRoleUser(userLogin.getAuthorities().toString());
		}
	}

	@GetMapping(value = "/book")
	public BookOutput getAll(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false, defaultValue = "id") String order,
			@RequestParam(value = "search", required = false) String search) {

		checkLogin();
		BookOutput result = new BookOutput();
		List<BookDTO> listBookResult = null;
		// userLogin
		if (page != null && limit != null) {
			Sort objSort = new Sort(Sort.Direction.ASC, order);
			if ("DESC".equalsIgnoreCase(sort)) {
				objSort = new Sort(Direction.DESC, order);
			}
			Pageable pageable = PageRequest.of(page - 1,  limit, objSort);
			result.setPage(page);
			
			/* Pageable pageable = new PageRequest(page - 1, limit, objSort); */
			// check userRole
			if (("ADMIN").equals(userRoles)) {
				listBookResult = bookService.findAll(pageable);

			} else if (("USER").equals(userRoles)) {
				String userName = userLogin.getUsername();
				listBookResult = bookService.findAllByUserCreated(userName, pageable);
			} else {
				if (search != null) {
					search = "%" + search + "%";
					listBookResult = bookService.findAllEnableSearch(search, pageable);
				} else {
					listBookResult = bookService.findAllEnable(pageable);
				}
			}
			
			int totalItem = bookService.totalItem(listBookResult);
			result.setTotalPage(PageUtil.totalPage(totalItem, limit));

		} else {
			listBookResult = bookService.findAll();
		}
		result.setListResult(listBookResult);
		return result;

	}

	@GetMapping(value = "/book/{id}")
	public BookDTO getOneById(@PathVariable long id) {
		return bookService.getById(id);

	}

	/*
	 * @GetMapping(value = "/book/search") public BookDTO search(@PathVariable
	 * String search) {
	 * 
	 * return null;
	 * 
	 * }
	 */
	@DeleteMapping(value = "/book")
	public void deleteBook(@RequestBody long[] ids) {
		checkLogin();
		for (Long id : ids) {
			BookDTO book = bookService.getById(id);
			if (!userLogin.getUsername().equals(book.getCreatedBy())) {
				throw new UnauthorizedException();
			} 
		}
		
			// delete all comment by idBook
			commentService.deleteByBook(ids);
			System.out.println("1");
			bookService.delete(ids);

	}

	@PostMapping(value = "/book")
	public BookDTO insertBook(@RequestBody BookDTO book) {
		if (bookService.hadBook(book)) {
			throw new BookExistionException();
		}
		return bookService.save(book);
	}

	@PutMapping(value = "/book/{id}")
	public BookDTO editBook(@RequestBody BookDTO book, @PathVariable long id) {

		book.setId(id);
		BookDTO bookDTO = bookService.getById(id);
		checkLogin();
		
		if (("ADMIN").equals(userRoles) || userLogin.getUsername().equals(bookDTO.getCreatedBy())) {
			return bookService.save(book);
		} else {
			throw new UnauthorizedException();
		}

	}

}
