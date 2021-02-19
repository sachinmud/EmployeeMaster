package com.paypal.bfs.test.employeeserv.exception.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {

	private String title;
	private String detail;
	private String timeStamp;
	private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();

}
