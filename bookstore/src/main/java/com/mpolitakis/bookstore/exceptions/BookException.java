package com.mpolitakis.bookstore.exceptions;

import org.springframework.http.HttpStatus;

public class BookException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus errorStatus;
	private String errorMessage;

	public BookException() {

	}

	public BookException(String errorMessage, HttpStatus errorStatus) {
		super(errorMessage);
		this.errorStatus = errorStatus;
		this.errorMessage = errorMessage;
	}

	public HttpStatus getErrorStatus() {
		return errorStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
