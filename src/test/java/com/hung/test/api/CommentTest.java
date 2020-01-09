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
		CommentDTO comment = new CommentDTO("hay");
		CommentDTO comment2 = new CommentDTO("hay qua");
		List<CommentDTO> listComment = Arrays.asList(comment, comment2);
		given(commentService.findAllByBook(1)).willReturn(listComment);

		mvc.perform(get("/api/comments/{bid}",1)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].message", is(listComment.get(0).getMessage())));
	}

	@Test
	public void insertComment() throws Exception {
		CommentDTO comment = new CommentDTO("hay");
		long bookId = 1;
		given(commentService.save(comment, bookId)).willReturn(comment);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/comments/{bid}",bookId)
			    .content(asJsonString( new CommentDTO("hay"))) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(comment.getMessage())));
	}
	
	@Test
	public void editComment() throws Exception {
		CommentDTO comment = new CommentDTO("hay");
		long id = 1;
		comment.setId(id);
		given(commentService.save(comment, id)).willReturn(comment);
		
		mvc.perform(MockMvcRequestBuilders.put("/api/comments/{bid}/{id}", id, id)
				.content(asJsonString(new CommentDTO("hay"))) 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(comment.getMessage())));
	}
	
	@Test
	public void deleteComment() throws Exception {
		mvc.perform( MockMvcRequestBuilders.delete("/api/comments/{id}", 1))
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
