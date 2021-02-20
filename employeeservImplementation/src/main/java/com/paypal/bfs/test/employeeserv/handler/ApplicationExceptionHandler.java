package com.paypal.bfs.test.employeeserv.handler;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paypal.bfs.test.employeeserv.exception.InternalServerErrorException;
import com.paypal.bfs.test.employeeserv.exception.ResourceAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.exception.ResourceNotFoundException;
import com.paypal.bfs.test.employeeserv.exception.vo.ErrorDetail;
import com.paypal.bfs.test.employeeserv.exception.vo.ValidationError;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler  {

	@Autowired
	private MessageSource messageSource;
	
	SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSZ");
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(dtFormat.format(new Date()));
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(rnfe.getMessage());
		
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}	

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceAlreadyExistsException rae, HttpServletRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(dtFormat.format(new Date()));
		errorDetail.setTitle("Resource already exists");
		errorDetail.setDetail(rae.getMessage());
		
		return new ResponseEntity<>(errorDetail, null, HttpStatus.CONFLICT);
	}	

	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<?> handleResourceNotFoundException(InternalServerErrorException ise, HttpServletRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(dtFormat.format(new Date()));
		errorDetail.setTitle("Internal Server Error");
		errorDetail.setDetail(ise.getMessage());
		
		return new ResponseEntity<>(errorDetail, null, HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException cve, HttpServletRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(dtFormat.format(new Date()));
		errorDetail.setTitle("Validation Failed");
		errorDetail.setDetail("Input validation failed");
		List<String> fieldErrors = new ArrayList<>(cve.getConstraintViolations().size());
		Set<ConstraintViolation<?>> validationErrors = cve.getConstraintViolations();
		
		validationErrors.forEach(e -> {
			fieldErrors.add(e.getMessage());
		});
		
		errorDetail.setFieldErrors(fieldErrors);
	 	
		return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
	}	

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(dtFormat.format(new Date()));
		errorDetail.setTitle("Message Not Readable");
		errorDetail.setDetail(ex.getMessage());
		
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}

}
