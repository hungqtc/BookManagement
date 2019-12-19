package com.hung.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hung.handlers.exceptions.BookExistionException;
import com.hung.handlers.exceptions.UnauthorizedException;
import com.hung.handlers.exceptions.UserExistionException;
import com.hung.handlers.exceptions.message.ErrorMessage;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorMessage> getNoAuthorMessage() {
		ErrorMessage message = new ErrorMessage();
		message.setStatusCode(403);
		message.setMessage("Full authentication is required to access this resource");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<ErrorMessage> getIndexOutOfBoundsMessage() {
		ErrorMessage message = new ErrorMessage();
		message.setStatusCode(400);
		message.setMessage("This object doesn't exist");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BookExistionException.class)
	public ResponseEntity<ErrorMessage> getBookExistionMessage() {
		ErrorMessage message = new ErrorMessage();
		message.setStatusCode(400);
		message.setMessage("This book existed");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserExistionException.class)
	public ResponseEntity<ErrorMessage> getUserExistionMessage() {
		ErrorMessage message = new ErrorMessage();
		message.setStatusCode(400);
		message.setMessage("This user existed");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}
}
