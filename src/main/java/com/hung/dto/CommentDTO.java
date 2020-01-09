package com.hung.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO extends BaseDTO {
	@NotBlank(message = "Please provide a message")
	private String message;
	private String bookTitle;
}
