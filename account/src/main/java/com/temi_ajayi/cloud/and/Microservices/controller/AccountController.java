package com.temi_ajayi.cloud.and.Microservices.controller;

import com.temi_ajayi.cloud.and.Microservices.dto.request.AccountsContactInfoDto;
import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.CustomerResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCustomerResponseDto;
import com.temi_ajayi.cloud.and.Microservices.service.impl.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;



    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDto> createAccount (@RequestBody CustomerDto customerDto){
        return accountService.createAccount(customerDto);
    }

    @PostMapping("/fetch")
    public ResponseEntity<FetchCustomerResponseDto> fetchCustomer(@RequestBody CustomerDto customerDto){
        return accountService.fetchAccount(customerDto);
    }

    @PostMapping("/delete")
    public ResponseEntity<CustomerResponseDto> deleteAccount(@RequestBody CustomerDto customerDto){
        return accountService.deleteAccount(customerDto);
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        log.info("Contact Details:::{}", accountsContactInfoDto.getMessage());
        log.info("Contact Details:::{}", accountsContactInfoDto.getContactDetails());
        log.info("Contact Details:::{}", accountsContactInfoDto.getOnCallSupport());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);

    }
}
