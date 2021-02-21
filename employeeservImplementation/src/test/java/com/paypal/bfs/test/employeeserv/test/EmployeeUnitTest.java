package com.paypal.bfs.test.employeeserv.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.AddressRepository;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.domain.AddressEntity;
import com.paypal.bfs.test.employeeserv.domain.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeUnitTest {

	@Autowired
	EmployeeRepository empRepository;
	
	@Autowired
	AddressRepository addrRepository;
	
	private Integer employeeId;
	
	@Test
	public void testCreateEmployee() {
		AddressEntity address = new AddressEntity();
		address.setLine1("line1");
		address.setCity("city");
		address.setCountry("country");
		address.setState("state");
		address.setZipcode("zipcode");
		
		EmployeeEntity employee = new EmployeeEntity();
		employee.setFirstName("firstName");
		employee.setLastName("lastName");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2001-10-11", formatter);
		employee.setDateOfBirth(localDate);

		employee = empRepository.save(employee);
		assertNotNull(employee);
		employeeId = employee.getId();
		assertTrue(employeeId > 0);
	}
	
}
