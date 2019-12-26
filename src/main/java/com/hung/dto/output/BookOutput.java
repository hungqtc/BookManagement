package com.hung.dto.output;

import java.util.ArrayList;
import java.util.List;
import com.hung.dto.BookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookOutput {
	private int page;
	private int totalPage;
	private List<BookDTO> listResult = new ArrayList<>();
}
