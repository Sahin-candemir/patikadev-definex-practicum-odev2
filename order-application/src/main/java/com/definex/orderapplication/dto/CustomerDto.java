package com.definex.orderapplication.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

	private String name;
	
	private String surname;
	
	private Date createdDate;
}
