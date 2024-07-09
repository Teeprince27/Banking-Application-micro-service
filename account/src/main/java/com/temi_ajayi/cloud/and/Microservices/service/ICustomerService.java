package com.temi_ajayi.cloud.and.Microservices.service;

import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDetailRequestDto;
import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDetailsDto;
import org.springframework.http.ResponseEntity;

public interface ICustomerService {

    ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(CustomerDetailRequestDto customerDetailRequestDto);
}
