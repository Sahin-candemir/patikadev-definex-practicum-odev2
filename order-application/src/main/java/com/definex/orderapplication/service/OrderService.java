package com.definex.orderapplication.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.definex.orderapplication.dto.CustomerDto;
import com.definex.orderapplication.dto.InvoiceDto;
import com.definex.orderapplication.model.Customer;
import com.definex.orderapplication.model.Invoice;
import com.definex.orderapplication.repository.CustomerRepository;
import com.definex.orderapplication.repository.InvoiceRepository;

@Service
public class OrderService {
	
	private CustomerRepository customerRepository;
	
	private InvoiceRepository invoiceRepository;
	
	public OrderService(CustomerRepository customerRepository, InvoiceRepository invoiceRepository) {
		super();
		this.customerRepository = customerRepository;
		this.invoiceRepository = invoiceRepository;
	}

	public List<CustomerDto> getAllCustomerList(){
		List<Customer> customers =  customerRepository.findAll();
		
		return customers.stream().map(customer->mapToCustomerDto(customer)).collect(Collectors.toList());
	}
	
	public void createCustomer(CustomerDto customerDto) {
		Customer customer = new Customer();
		Date date = new Date();
		customer.setName(customerDto.getName());
		customer.setSurname(customerDto.getSurname());
		customer.setCratedDate(date);
		
		customerRepository.save(customer);
	}
	public List<CustomerDto> getAllCustomerByContainingC(){
		List<Customer> customers = customerRepository.findByContainingC();
		return customers.stream().map(customer->mapToCustomerDto(customer)).collect(Collectors.toList());
	}
	public BigDecimal getAllCustomerCreatedInJune(){
		List<Customer> customers = customerRepository.findByJune();
		List<Long> customerIdList = customers.stream().map(customer -> customer.getId()).collect(Collectors.toList());
		List<Invoice> invoices = customerIdList.stream().map(customerId -> invoiceRepository.findByCustomerId(customerId)).collect(Collectors.toList());
		Function<Invoice, BigDecimal> mapper = invoice->invoice.getAmount();
		
		return invoices.stream().map(mapper).reduce(BigDecimal.ZERO,BigDecimal::add);
	}

	//Tüm faturaların listesini alır.
	public List<InvoiceDto> getAllInvoices(){
		List<Invoice> invoices = invoiceRepository.findAll();
		
		return invoices.stream().map(invoice-> mapToInvoiceDto(invoice)).collect(Collectors.toList());
	}
	//Verilen değerin(1500TL üstü faturalar) üstündeki faturaların listesini alır
	//istenilen değerin değiştirilebilmesi için alınan değere göre arama yapılır
	public List<InvoiceDto> getAllInvoicesGreaterThanValue(BigDecimal value){
		List<Invoice> invoices = invoiceRepository.findByAmountGreaterThan(value);
		return invoices.stream().map(invoice-> mapToInvoiceDto(invoice)).collect(Collectors.toList());
	}
	//Verilen değerin(1500Tl üstü faturalar) faturaların ortalamasını alır.
	public BigDecimal getAverageAllInvoiceGreaterThanValue(BigDecimal value) {
		List<Invoice> invoices = invoiceRepository.findByAmountGreaterThan(value);
		Long invoiceCount = invoices.stream().count();
		Function<Invoice, BigDecimal> mapper = invoice->invoice.getAmount();
		
		return invoices.stream().map(mapper) .reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(invoiceCount));
	}
	//Verilen değerin(500TL) altındki faturaya sahip müşterilerin isimlerini alır.
	public List<String> getAllCustomerNameWithInvoiceUnderValue(BigDecimal value){
		List<Invoice> invoices = invoiceRepository.findByAmountLessThan(value);
		
		List<Long> customerIdList = invoices.stream().map(invoice -> invoice.getCustomer().getId()).toList();
		List<String> customerName = customerIdList.stream().map(customerId -> customerRepository.findById(customerId).get().getName()).collect(Collectors.toList());
	
		return customerName;
	}
	
	
	private InvoiceDto mapToInvoiceDto(Invoice invoice) {
		InvoiceDto invoiceDto = new InvoiceDto();
		invoiceDto.setAmount(invoice.getAmount());
		return invoiceDto;
	}

	private CustomerDto mapToCustomerDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setName(customer.getName());
		customerDto.setSurname(customer.getSurname());
		return customerDto;
	}
}
