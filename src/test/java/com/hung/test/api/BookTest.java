package com.hung.test.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.LongArrayAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hung.controller.BookController;
import com.hung.controller.UserController;
import com.hung.dto.BookDTO;
import com.hung.dto.UserDTO;
import com.hung.dto.output.BookOutput;
import com.hung.service.BookService;
import com.hung.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class, secure = false)
public class BookTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	BookService bookService;

	@Test
	public void getBook() throws Exception {
		Sort objSort = new Sort(Sort.Direction.ASC, "title");
		Pageable pageable = PageRequest.of(0, 2, objSort);
		BookOutput bookOutput = new BookOutput();
		BookDTO book = new BookDTO("Ngồi Khóc Trên Cây", null, "Nguyễn Nhật Ánh", null, 1);
		book.setId((long) 1);
		BookDTO book2 = new BookDTO("Mắt Biếc", null, "Nguyễn Nhật Ánh", null, 1);
		book2.setId((long) 2);
		BookDTO book3 = new BookDTO("Thằng Quỷ Nhỏ", null, "Nguyễn Nhật Ánh", null, 1);
		book3.setId((long) 3);
		List<BookDTO> listBook = Arrays.asList(book2, book, book3);
		bookOutput.setPage(1);
		bookOutput.setTotalPage(3);
		bookOutput.setListResult(listBook);
		given(bookService.findAll(pageable)).willReturn(bookOutput);

		mvc.perform(get("/book")

				.param("page", "1")
				.param("limit", "2")
				.param("sort", "ASC")
				.param("order", "title")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.listResult", hasSize(3)))
				.andExpect(jsonPath("$.page", is(pageable.getPageNumber()+1)))
				.andExpect(jsonPath("$.listResult[0].id", is(2)));
	}
	
	@Test
	public void getBookSearch() throws Exception {
		Sort objSort = new Sort(Sort.Direction.ASC, "title");
		Pageable pageable = PageRequest.of(0, 2, objSort);
		BookOutput bookOutput = new BookOutput();
		BookDTO book = new BookDTO("Ngồi Khóc Trên Cây", null, "Nguyễn Nhật Ánh", null, 1);
		book.setId((long) 1);
		List<BookDTO> listBook = Arrays.asList( book);
		bookOutput.setPage(1);
		bookOutput.setTotalPage(1);
		bookOutput.setListResult(listBook);
		given(bookService.findAllEnableSearch("ngoi", pageable)).willReturn(bookOutput);

		mvc.perform(get("/book")
			.param("page", "1")
			.param("limit", "2")
			.param("order", "title")
			.param("search", "ngoi")
			.contentType(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.listResult", hasSize(1)))
			.andExpect(jsonPath("$.page", is(1)))
			.andExpect(jsonPath("$.listResult[0].id", is(1)));
	}

	@Test
	public void getBookById() throws Exception {

		long id = 1;
		BookDTO book = new BookDTO("Ngồi Khóc Trên Cây", null, "Nguyễn Nhật Ánh", null, 1);
		book.setId(id);

		given(bookService.findById(id)).willReturn(book);

		mvc.perform(get("/book/{id}", id).contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is(book.getTitle())))
			.andExpect(jsonPath("$.author", is(book.getAuthor())));
	}

	@Test
	public void insertBook() throws Exception {

		BookDTO book = new BookDTO("Ngồi Khóc Trên Cây", null, "Nguyễn Nhật Ánh", null, 1);
		given(bookService.save(book)).willReturn(book);

		mvc.perform(MockMvcRequestBuilders.post("/book")

		    .content(asJsonString(book))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is(book.getTitle())));

	}

	@Test
	public void editBook() throws Exception {
		long id = 1;
		BookDTO book = new BookDTO("Ngồi Khóc Trên Cây", null, "Nguyễn Nhật Ánh", null, 1);
		book.setId(id);
		given(bookService.save(book)).willReturn(book);

		mvc.perform(MockMvcRequestBuilders.put("/book/{id}", id)

		   .content(asJsonString(book))
		   .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		   .andDo(print())
		   .andExpect(status().isOk()).andExpect(jsonPath("$.title", is(book.getTitle())));
			
	}

	@Test
	public void deleteBook() throws Exception {
		List<Long> list = new ArrayList<Long>();
		list.add((long) 5);
		list.add((long) 1);
		
		mvc.perform(MockMvcRequestBuilders.delete("/book") 
		    .content(asJsonString(list))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
