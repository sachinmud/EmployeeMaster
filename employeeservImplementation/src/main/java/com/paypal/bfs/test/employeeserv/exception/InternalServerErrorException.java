package com.paypal.bfs.test.employeeserv.exception;

public class InternalServerErrorException extends Exception {
	
	public InternalServerErrorException() {}
	
	public InternalServerErrorException(String message) {
		super(message);
	}
	
	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}
	
	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
