package com.hung.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BookDTO extends BaseDTO{
	@NotBlank(message = "Please provide a title") 
	private String title;
	private String description;
	
	@NotBlank(message = "Please provide an author") 
	private String author;
	private String image;
	private int status;
}
