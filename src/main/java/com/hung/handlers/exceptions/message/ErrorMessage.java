package com.hung.handlers.exceptions.message;

import lombok.Data;

@Data
public class ErrorMessage {
	
	 private int statusCode;
	 private String message;
}
