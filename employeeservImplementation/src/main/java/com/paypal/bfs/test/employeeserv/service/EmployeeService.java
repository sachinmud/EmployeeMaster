package com.paypal.bfs.test.employeeserv.service;

import javax.validation.Valid;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.domain.EmployeeEntity;

public interface EmployeeService {

	public Employee getEmployee(String employeeId) throws Exception ;

	public Employee createEmployee(Employee employee) throws Exception ;
}
