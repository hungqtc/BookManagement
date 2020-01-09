
package com.hung.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hung.dto.BookDTO;
import com.hung.dto.output.BookOutput;
import com.hung.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	BookService bookService;
	
	@GetMapping()
	public BookOutput getAll(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "order", required = false, defaultValue = "id") String order,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "status", required = false, defaultValue = "1")  Integer status) {
		return bookService.findAll(page, limit, sort, order, search, status);
	}

	@GetMapping(value = "/{id}")
	public BookDTO getOneById(@PathVariable long id) {
		return bookService.findById(id);
	}

	@DeleteMapping()
	public void deleteBook(@RequestBody long[] ids) {
		bookService.delete(ids);
	}

	@PostMapping()
	public BookDTO insertBook(@Valid @RequestBody BookDTO book) {
		return bookService.save(book);
	}

	@PutMapping(value = "/{id}")
	public BookDTO editBook(@Valid @RequestBody BookDTO book, @PathVariable long id) {
		book.setId(id);
		return bookService.save(book);
	}

}
