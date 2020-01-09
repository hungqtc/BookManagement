
package com.hung.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hung.dto.CommentDTO;
import com.hung.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	CommentService commentService;

	@GetMapping(value = "/{bid}")
	public List<CommentDTO> getAll(@PathVariable long bid) {
		return commentService.findAllByBook(bid);
	}

	@DeleteMapping(value = "/{id}")
	public void deleteComment(@PathVariable long id) {
		commentService.delete(id);
	}

	@PostMapping(value = "/{bid}")
	public CommentDTO insertComment(@Valid @RequestBody CommentDTO comment, @PathVariable long bid) {
		return commentService.save(comment, bid);
	}

	@PutMapping(value = "/{bid}/{id}")
	public CommentDTO editComment(@Valid @RequestBody CommentDTO comment, @PathVariable long bid, @PathVariable long id) {
		comment.setId(id);
		return commentService.save(comment, bid);
	}
}
