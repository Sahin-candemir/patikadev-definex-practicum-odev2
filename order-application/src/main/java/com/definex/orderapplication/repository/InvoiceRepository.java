package com.definex.orderapplication.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.definex.orderapplication.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	List<Invoice> findByAmountGreaterThan(BigDecimal value);

	List<Invoice> findByAmount(BigDecimal value);

	List<Invoice> findByAmountLessThan(BigDecimal value);
	
	Invoice findByCustomerId(Long customerId);

}
