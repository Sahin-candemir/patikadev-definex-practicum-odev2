package com.definex.orderapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.definex.orderapplication.service.OrderService;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
		
		final OrderService orderService = null;
		
		System.out.println(orderService.getAllCustomerList());
		
	}

}
