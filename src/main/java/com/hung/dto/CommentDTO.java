package com.hung.dto;

import lombok.Data;


@Data 
public class CommentDTO extends BaseDTO{
	private String message;
	private String bookTitle;
}
