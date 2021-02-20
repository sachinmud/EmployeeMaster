package com.paypal.bfs.test.employeeserv.exception;

public class ResourceAlreadyExistsException extends Exception {
	
	public ResourceAlreadyExistsException() {}
	
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
	
	public ResourceAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public ResourceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
