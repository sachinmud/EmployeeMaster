package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.InternalServerErrorException;
import com.paypal.bfs.test.employeeserv.exception.ResourceAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	EmployeeService service;
	
    @Override
    public ResponseEntity<Employee> employeeGetById(String id) throws Exception {
    	Employee employee = service.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) throws Exception {
    	try {
	    	employee = service.createEmployee(employee);
	        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    	} catch(DataIntegrityViolationException e) {
    		if(e.getCause() instanceof ConstraintViolationException) {
    			throw new ResourceAlreadyExistsException("Employee already exists", e);
    		} else {
    			throw new InternalServerErrorException(e);
    		}
        }
    }
}
