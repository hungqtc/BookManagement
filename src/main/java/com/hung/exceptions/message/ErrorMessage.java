package com.hung.exceptions.message;

import lombok.Data;

@Data
public class ErrorMessage {
	 private int statusCode;
	 private String message;
}
