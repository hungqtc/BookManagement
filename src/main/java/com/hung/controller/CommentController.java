
package com.hung.controller;

import java.util.List;

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
import com.hung.dto.CommentDTO;
import com.hung.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	CommentService commentService;

	@GetMapping
	public List<CommentDTO> getAll(@RequestParam(value = "bookId", required = true) long bookId) {
		return commentService.findAllByBook(bookId);
	}

	@GetMapping(value = "/{id}")
	public CommentDTO getOneById(@PathVariable long id) {
		return commentService.findById(id);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteComment(@PathVariable long id) {
		commentService.delete(id);
	}

	@PostMapping
	public CommentDTO insertComment(@RequestBody CommentDTO comment, @RequestParam(value = "bookId", required = true) long bookId) {
		return commentService.save(comment, bookId);
	}

	@PutMapping(value = "/{id}")
	public CommentDTO editComment(@RequestBody CommentDTO comment, @PathVariable long id, @RequestParam(value = "bookId", required = true) long bookId) {
		comment.setId(id);
		return commentService.save(comment, bookId);
	}
}
