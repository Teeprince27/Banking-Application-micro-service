package com.temi_ajayi.cloud.and.Microservices.service;

import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.CustomerResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCustomerResponseDto;
import org.springframework.http.ResponseEntity;

public interface IAccountService {


    ResponseEntity<CustomerResponseDto> createAccount(CustomerDto customerDto);


    ResponseEntity<FetchCustomerResponseDto> fetchAccount(CustomerDto customerDto);


    boolean updateAccount(CustomerDto customerDto);


    ResponseEntity<CustomerResponseDto> deleteAccount(CustomerDto customerDto);

}
