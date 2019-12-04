package com.hung.dto;

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
	private String title;
	private String description;
	private String author;
	private String image;
	private int status;
}
