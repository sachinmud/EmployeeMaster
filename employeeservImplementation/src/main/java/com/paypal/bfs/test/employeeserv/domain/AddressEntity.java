package com.paypal.bfs.test.employeeserv.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ADDRESS")
public class AddressEntity implements Serializable {
	
	@Id
	@Column(name = "ADDRESSID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "LINE1", length = 255, nullable = false)
	private String line1;
	
	@Column(name = "LINE2", length = 255, nullable = true)
	private String line2;
	
	@Column(name = "CITY", length = 150, nullable = false)
	private String city;
	
	@Column(name = "STATE", length = 150, nullable = false)
	private String state;
	
	@Column(name = "COUNTRY", length = 150, nullable = false)
	private String country;
	
	@Column(name = "ZIPCODE", length = 150, nullable = false)
	private String zipcode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEEID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private EmployeeEntity employee;
}
