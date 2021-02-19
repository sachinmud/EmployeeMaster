package com.paypal.bfs.test.employeeserv;

import static org.junit.Assert.assertNotNull;

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

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.AddressRepository;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.domain.AddressEntity;
import com.paypal.bfs.test.employeeserv.domain.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;

@DataJpaTest
public class EmployeeTest {

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
		employee.setDateOfBirth(new Date());

		employee = empRepository.save(employee);
		assertNotNull(employee);
		employeeId = employee.getId();
	}
	
}
