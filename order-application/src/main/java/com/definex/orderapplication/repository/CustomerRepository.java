package com.definex.orderapplication.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.definex.orderapplication.model.Customer;
import com.definex.orderapplication.model.Invoice;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	List<Customer> findByContainingC();

	@Query(value = "SELECT * FROM customer c WHERE MONTH( c.created_date)=6", nativeQuery = true)
	List<Customer> findByJune();

	
		

	
}
