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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
	@NotEmpty(message = "{line1.notempty}")
	@Size(max = 255, message = "{line1.size}")
	private String line1;
	
	@Column(name = "LINE2", length = 255, nullable = true)
	@Size(max = 255, message = "{line2.size}")
	private String line2;
	
	@Column(name = "CITY", length = 150, nullable = false)
	@NotEmpty(message = "{city.notempty}")
	@Size(max = 150, message = "{city.size}")
	private String city;
	
	@Column(name = "STATE", length = 150, nullable = false)
	@NotEmpty(message = "{state.notempty}")
	@Size(max = 150, message = "{state.size}")
	private String state;
	
	@Column(name = "COUNTRY", length = 150, nullable = false)
	@NotEmpty(message = "{country.notempty}")
	@Size(max = 150, message = "{country.size}")
	private String country;
	
	@Column(name = "ZIPCODE", length = 150, nullable = false)
	@NotEmpty(message = "{zipcode.notempty}")
	@Size(max = 10, message = "{zipcode.size}")
	private String zipcode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEEID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private EmployeeEntity employee;
}
