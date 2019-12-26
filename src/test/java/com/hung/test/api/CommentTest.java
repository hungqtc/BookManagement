package com.hung.test.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hung.controller.CommentController;
import com.hung.dto.CommentDTO;
import com.hung.service.CommentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommentController.class, secure = false)
public class CommentTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	CommentService commentService;

	@Test
	public void getComment() throws Exception {
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");
		CommentDTO comment = new CommentDTO("hay", "Ngồi Khóc Trên Cây");
		CommentDTO comment2 = new CommentDTO("hay qua", "Ngồi Khóc Trên Cây");
		List<CommentDTO> listComment = Arrays.asList(comment, comment2);
		given(commentService.findAll()).willReturn(listComment);

		mvc.perform(get("/api/comments").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].message", is(listComment.get(0).getMessage())))
				.andExpect(jsonPath("$[0].bookTitle", is(listComment.get(0).getBookTitle())));
	}

	@Test
	public void getCommentById() throws Exception {
		long id = 1;
		CommentDTO comment = new CommentDTO("hay", "Ngồi Khóc Trên Cây");
		given(commentService.findById(id)).willReturn(comment);

		mvc.perform(get("/api/comments/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(comment.getMessage())))
				.andExpect(jsonPath("$.bookTitle", is(comment.getBookTitle())));
	}

	@Test
	public void insertComment() throws Exception {
		CommentDTO comment = new CommentDTO("hay", "Ngồi Khóc Trên Cây");
		given(commentService.save(comment)).willReturn(comment);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/comments")
			    .content(asJsonString( new CommentDTO("hay", "Ngồi Khóc Trên Cây"))) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(comment.getMessage())));
	}
	
	@Test
	public void editComment() throws Exception {
		CommentDTO comment = new CommentDTO("hay", "Ngồi Khóc Trên Cây");
		long id = 1;
		comment.setId(id);
		given(commentService.save(comment)).willReturn(comment);
		
		mvc.perform(MockMvcRequestBuilders.put("/api/comments/{id}", id)
				.content(asJsonString(new CommentDTO("hay", "Ngồi Khóc Trên Cây"))) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(comment.getMessage())));
	}
	
	@Test
	public void deleteComment() throws Exception {
		List<Long> list = new ArrayList<>();
		list.add((long) 5);
		list.add((long) 1);
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/comments") 
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
