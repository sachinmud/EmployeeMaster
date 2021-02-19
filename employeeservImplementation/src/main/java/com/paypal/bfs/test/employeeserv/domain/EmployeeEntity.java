package com.paypal.bfs.test.employeeserv.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

	@Id
	@Column(name = "EMPLOYEEID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "FIRSTNAME", length = 255, nullable = false)
	private String firstName;
	
	@Column(name = "LASTNAME", length = 255, nullable = false)
	private String lastName;
	
	@Column(name = "DATEOFBIRTH", length = 10, nullable = true)
	private Date dateOfBirth;
	
}
