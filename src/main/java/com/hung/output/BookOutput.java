package com.hung.output;

import java.util.ArrayList;
import java.util.List;

import com.hung.dto.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookOutput {
	private int page;
	private int totalPage;
	private List<BookDTO> listResult = new ArrayList<>();
	
	
}
