package com.paypal.bfs.test.employeeserv.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EMPLOYEE", uniqueConstraints = {@UniqueConstraint(columnNames={"FIRSTNAME", "LASTNAME", "DATEOFBIRTH"}, name = "UNIQUE_EMP")})
public class EmployeeEntity implements Serializable {

	@Id
	@Column(name = "EMPLOYEEID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "FIRSTNAME", length = 255, nullable = false)
	@NotEmpty(message = "{firstname.notempty}")
	@Size(max = 255, message = "{firstname.size}")
	private String firstName;
	
	@Column(name = "LASTNAME", length = 255, nullable = false)
	@NotEmpty(message = "{lastname.notempty}")
	@Size(max = 255, message = "{lastname.size}")
	private String lastName;
	
	@Column(name = "DATEOFBIRTH", nullable = true)
	@Past(message = "{dateOfBirth.past}")
	private LocalDate dateOfBirth;
	
}
