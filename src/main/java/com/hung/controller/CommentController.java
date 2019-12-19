
package com.hung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hung.dto.CommentDTO;
import com.hung.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	CommentService commentService;

	@GetMapping(value = "/comment")
	public List<CommentDTO> getAll() {
		
		return commentService.findAll();
	}

	@GetMapping(value = "/comment/{id}")
	public CommentDTO getOneById(@PathVariable long id) {
		
		return commentService.findById(id);
	}

	@DeleteMapping(value = "/comment")
	public void deleteComment(@RequestBody long[] ids) {
		
		commentService.delete(ids);
	}

	@PostMapping(value = "/comment")
	public CommentDTO insertComment(@RequestBody CommentDTO comment) {
		return commentService.save(comment);
	}

	@PutMapping(value = "/comment/{id}")
	public CommentDTO editComment(@RequestBody CommentDTO comment, @PathVariable long id) {
		comment.setId(id);
		return commentService.save(comment);
	}

}
