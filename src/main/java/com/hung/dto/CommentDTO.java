package com.hung.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO extends BaseDTO {
	private String message;
	private String bookTitle;
}
