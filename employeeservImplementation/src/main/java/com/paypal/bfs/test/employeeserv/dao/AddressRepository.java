package com.paypal.bfs.test.employeeserv.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paypal.bfs.test.employeeserv.domain.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>
{
	@Query(value = "select * from address where addressid = :addressId", nativeQuery = true)
	List<AddressEntity> findByEmployeeId(@Param("addressId") Integer addressId);
}
