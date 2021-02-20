package com.paypal.bfs.test.employeeserv.exception.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {

	private String title;
	private String detail;
	private String timeStamp;
	private List<String> fieldErrors;

}
