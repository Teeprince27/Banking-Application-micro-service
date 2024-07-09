package com.temi_ajayi.cloud.and.Microservices.controller;

import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDetailRequestDto;
import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDetailsDto;
import com.temi_ajayi.cloud.and.Microservices.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})


public class CustomerController {

    private ICustomerService customerService;

    private CustomerController (ICustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestBody CustomerDetailRequestDto customerDetailRequestDto){
       CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(customerDetailRequestDto).getBody();
       return ResponseEntity.ok().body(customerDetailsDto);
    }
}
