package com.paypal.bfs.test.employeeserv.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.AddressRepository;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.domain.AddressEntity;
import com.paypal.bfs.test.employeeserv.domain.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.InternalServerErrorException;
import com.paypal.bfs.test.employeeserv.exception.ResourceAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.exception.ResourceNotFoundException;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.utils.EntityUtil;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepository;
	
	@Autowired
	AddressRepository addrRepository;
	
	@Autowired
    private Validator validator;

	SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	public Employee getEmployee(String employeeId) throws Exception {
		
		Employee employee = null;
		EmployeeEntity empEntity = null;
		
		try {
			Optional<EmployeeEntity> opempEntity = empRepository.findById(Integer.parseInt(employeeId));
			if(opempEntity.isPresent()) {
				empEntity = opempEntity.get();
			}
			else {
				throw new ResourceNotFoundException("Employee not found");
			}
			employee = new EntityUtil<EmployeeEntity, Employee>().copyProperties(empEntity,  new Employee());
			List<AddressEntity> addressEntities = addrRepository.findByEmployeeId(empEntity.getId());
			if(addressEntities != null & addressEntities.size() > 0) {
				List<Address> addresses = new ArrayList<Address>();
				addressEntities.forEach(a -> {
					Address address = new EntityUtil<AddressEntity, Address>().copyProperties(a, new Address());
					addresses.add(address);
				});
				employee.setAddress(addresses);
			}
		} catch(ResourceNotFoundException e) {
			logger.error(e);
			throw e;
		} catch(EntityNotFoundException e) {
			logger.error(e);
			throw new ResourceNotFoundException("Employee not found", e);
		} catch(Exception e) {
			logger.error(e);
			throw new InternalServerErrorException(e);
		}
		
		return employee;
	}
	
	public Employee createEmployee(Employee employee)  throws Exception {
		try {
			EmployeeEntity empEntity = new EntityUtil<Employee, EmployeeEntity>().copyProperties(employee, new EmployeeEntity());
			validateEmployee(empEntity);
			empEntity = empRepository.save(empEntity);
			employee.setId(empEntity.getId());
			if(employee.getAddress() != null && employee.getAddress().size() > 0) {
				for(int i=0; i<employee.getAddress().size(); i++) {
					Address address = employee.getAddress().get(i);
					AddressEntity addrEntity = new EntityUtil<Address, AddressEntity>().copyProperties(address, new AddressEntity());
					validateAddress(addrEntity);
					addrEntity.setEmployee(empEntity);
					addrRepository.save(addrEntity);
				}
			}
			
		} catch(DataIntegrityViolationException e) {
			logger.error(e);
			throw new ResourceAlreadyExistsException("Employee already exists", e);
		} catch(ConstraintViolationException e) {
			logger.error(e);
			throw e;
		} catch(Exception e) {
			logger.error(e);
			throw new InternalServerErrorException(e);
		}
		
		return employee;
	}

	private void validateEmployee(EmployeeEntity empEntity)  throws Exception {
		Set<ConstraintViolation<EmployeeEntity>> violations = validator.validate(empEntity);
		if(violations != null & violations.size() > 0) {
			throw new ConstraintViolationException(violations);
		}
	}

	private void validateAddress(AddressEntity addrEntity)  throws Exception {
		Set<ConstraintViolation<AddressEntity>> violations = validator.validate(addrEntity);
		if(violations != null & violations.size() > 0) {
			throw new ConstraintViolationException(violations);
		}
	}
}
