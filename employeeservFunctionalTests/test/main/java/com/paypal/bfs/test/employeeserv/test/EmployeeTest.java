package com.paypal.bfs.test.employeeserv.test;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.domain.AddressEntity;
import com.paypal.bfs.test.employeeserv.domain.EmployeeEntity;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {

	@Autowired
	MockMvc mockMvc;
	
	Employee employee;
	
	Employee employeeWithoutAddress;
	
	Employee employeeInvalid;
	
	Employee employeeWithInvalidAddress;

	@BeforeEach
	public void setup() {
		Address address = new Address();
		address.setLine1("line1");
		address.setCity("city");
		address.setCountry("country");
		address.setState("state");
		address.setZipcode("zipcode");
		List<Address> addresses = new ArrayList<>();
		addresses.add(address);
		
		Address invalidAddress = new Address();
		invalidAddress.setCity("city");
		invalidAddress.setCountry("country");
		invalidAddress.setState("state");
		invalidAddress.setZipcode("zipcode");
		List<Address> invalidAddresses = new ArrayList<>();
		invalidAddresses.add(invalidAddress);

		employee = new Employee();
		employee.setFirstName("firstName");
		employee.setLastName("lastName");
		employee.setDateOfBirth("22/01/1990");
		employee.setAddress(addresses);

		employeeWithoutAddress = new Employee();
		employeeWithoutAddress.setFirstName("firstName1");
		employeeWithoutAddress.setLastName("lastName1");
		employeeWithoutAddress.setDateOfBirth("22/01/1990");

		employeeInvalid = new Employee();
		employeeInvalid.setFirstName("firstName2");
		employeeInvalid.setDateOfBirth("22/01/1990");
		
		employeeWithInvalidAddress = new Employee();
		employeeWithInvalidAddress.setFirstName("firstName");
		employeeWithInvalidAddress.setLastName("lastName");
		employeeWithInvalidAddress.setDateOfBirth("22/01/1990");
		employeeWithInvalidAddress.setAddress(invalidAddresses);
	}
	
	@Test
	public void testCreateAndGetEmployee() {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			MvcResult result = mockMvc.perform(
		            MockMvcRequestBuilders.post("/v1/bfs/employees")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(mapper.writeValueAsString(employee)))
		            .andExpect(status().isCreated())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("firstName"))
		            .andReturn();
		    
			employee = mapper.readValue(result.getResponse().getContentAsString(), Employee.class);
			
			this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/"+employee.getId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateAndGetEmployeeWithoutAddress() {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			MvcResult result = mockMvc.perform(
		            MockMvcRequestBuilders.post("/v1/bfs/employees")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(mapper.writeValueAsString(employeeWithoutAddress)))
		            .andExpect(status().isCreated())
		            .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("firstName1"))
		            .andReturn();
		    
			employeeWithoutAddress = mapper.readValue(result.getResponse().getContentAsString(), Employee.class);
			
			this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/"+employeeWithoutAddress.getId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateInvalidEmployee() {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mockMvc.perform(
		            MockMvcRequestBuilders.post("/v1/bfs/employees")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(mapper.writeValueAsString(employeeInvalid)))
		            .andExpect(status().isBadRequest());
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateEmployeeWithInvalidAddress() {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mockMvc.perform(
		            MockMvcRequestBuilders.post("/v1/bfs/employees")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(mapper.writeValueAsString(employeeWithInvalidAddress)))
		            .andExpect(status().isBadRequest());
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEmployeeNotFound() {
		try {
			
			this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/100"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNotFound());
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testDuplicateEmployeeCreation() {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mockMvc.perform(
		            MockMvcRequestBuilders.post("/v1/bfs/employees")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(mapper.writeValueAsString(employee)))
		            .andExpect(status().isCreated());		    
			
			mockMvc.perform(
		            MockMvcRequestBuilders.post("/v1/bfs/employees")
		                    .contentType(MediaType.APPLICATION_JSON)
		                    .content(mapper.writeValueAsString(employee)))
		            .andExpect(status().isConflict());		    

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
