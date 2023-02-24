package com.sanjeev.blog.exceptions;

public class WrongPasswordException extends RuntimeException{

	public WrongPasswordException() {
		super();
	}

	public WrongPasswordException(String message) {
		super(message);
		
	}
	
}
