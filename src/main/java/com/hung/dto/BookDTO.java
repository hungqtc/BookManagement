package com.hung.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO extends BaseDTO{
	@NotBlank 
	private String title;
	private String description;
	
	@NotBlank 
	private String author;
	private String image;
	private int status;
}
