
package com.hung.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hung.constants.CommonConstant;
import com.hung.dto.BookDTO;
import com.hung.jwt.JwtTokenProvider;
import com.hung.output.BookOutput;
import com.hung.security.CustomUserDetails;
import com.hung.service.BookService;
import com.hung.utils.PageUtil;
import com.hung.utils.SecurityUtil;

import ch.qos.logback.core.joran.conditional.IfAction;

@RestController
public class BookController {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	BookService bookService;
	

	@GetMapping(value = "/book")
	public BookOutput getAll(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		
		BookOutput result = new BookOutput();
		// userLogin
		CustomUserDetails userLogin = (CustomUserDetails) SecurityUtil.getPrincipal();
		String roles = userLogin.getAuthorities().toString();

		if (page != null && limit != null) {
			result.setPage(page);
			Pageable pageable = new PageRequest(page - 1, limit);
			int totalItem = 0;
			 
			//check userRole
			if (SecurityUtil.checkRoleUser(roles).equals("ADMIN")) {
				result.setListResult(bookService.findAll(pageable));
				totalItem = bookService.totalItem();
			} else if (SecurityUtil.checkRoleUser(roles).equals("USER")) {
				String userName = userLogin.getUsername();
				result.setListResult(bookService.findAllByUserCreated(userName, pageable));
				totalItem = bookService.totalItemUserCreate(userName);
			} else {
				result.setListResult(bookService.findAllEnable(pageable));
				totalItem = bookService.totalItemStatus(CommonConstant.ENABLE);
			}

			result.setTotalPage(PageUtil.totalPage(totalItem, limit));

		} else {
			result.setListResult(bookService.findAll());
		} 
		return result;

	}

	@GetMapping(value = "/book/{id}")
	public BookDTO getOneById(@PathVariable long id) {
		return bookService.getById(id);
	}

	@DeleteMapping(value = "/book")
	public void deleteBook(@PathVariable long[] ids) {
		CustomUserDetails userLogin = (CustomUserDetails) SecurityUtil.getPrincipal();
		for (Long id : ids) {
			BookDTO book = bookService.getById(id);
			if (!userLogin.getUsername().equals(book.getCreatedBy())) {
				// throw new NoAuthDeleteException();
			}
		}
		//IdNotFoundException();
		bookService.delete(ids);
		
		//delete all comment by idBook
	}

	@PostMapping(value = "/book")
	public BookDTO insertBook(@RequestBody BookDTO book) {
		// check had book
		if (bookService.findByTitle(book.getTitle()) != null) {
			return null;
		}
		return bookService.save(book);
	}

	@PutMapping(value = "/book/{id}")
	public BookDTO editBook(@RequestBody BookDTO book, @PathVariable long id, @RequestParam(value = "status", required = false) Integer status) {
		if (status != null) {
			book.setStatus(status);
		}
		book.setId(id);
		CustomUserDetails userLogin = (CustomUserDetails) SecurityUtil.getPrincipal();
		BookDTO bookDTO = bookService.getById(id);
		if (userLogin.getAuthorities().toString().concat("[ROLE_ADMIN]") != null
				|| userLogin.getUsername().equals(bookDTO.getCreatedBy())) {
			return bookService.save(book);
		}
		return null;
	}

}
