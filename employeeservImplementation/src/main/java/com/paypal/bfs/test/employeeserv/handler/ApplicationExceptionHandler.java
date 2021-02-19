package com.paypal.bfs.test.employeeserv.handler;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(dtFormat.format(new Date()));
		errorDetail.setTitle("Validation Failed");
		errorDetail.setDetail("Input validation failed");
		
		// Create ValidationError instances
		List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
		for(FieldError fe : fieldErrors) {
			
			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
			if(validationErrorList == null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			validationError.setMessage(messageSource.getMessage(fe, null));
			validationErrorList.add(validationError);
		}
		
		return handleExceptionInternal(manve, errorDetail, headers, status, request);
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
