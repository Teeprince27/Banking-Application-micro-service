package com.temi_ajayi.cloud.and.Microservices.service.impl;

import com.temi_ajayi.cloud.and.Microservices.constant.AccountsConstant;
import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import com.temi_ajayi.cloud.and.Microservices.dto.request.AccountsDto;
import com.temi_ajayi.cloud.and.Microservices.dto.request.CustomerDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.CustomerResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCustomerResponseDto;
import com.temi_ajayi.cloud.and.Microservices.model.Accounts;
import com.temi_ajayi.cloud.and.Microservices.model.Customer;
import com.temi_ajayi.cloud.and.Microservices.repository.AccountRepository;
import com.temi_ajayi.cloud.and.Microservices.repository.CustomerRepository;
import com.temi_ajayi.cloud.and.Microservices.service.IAccountService;
import com.temi_ajayi.cloud.and.Microservices.utils.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    @Override
    public ResponseEntity<CustomerResponseDto> createAccount(CustomerDto customerDto) {

        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        HeaderBase headerBase = new HeaderBase();
        try{
            Customer customer = new Customer();
            customer.setMobileNumber(customerDto.getMobileNumber());
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());


            Optional<Customer> optionalCustomer = customerRepository.findFirstByMobileNumber(customerDto.getMobileNumber());
            log.info("checking if customer exist::: {}", optionalCustomer);
            if(optionalCustomer.isPresent()){
                headerBase.setResponseCode(ResponseCodes.ALREADY_EXIST_CODE);
                headerBase.setResponseMessage("Customer already registered with given mobileNumber ");
                customerResponseDto.setHeaderBase(headerBase);
                return ResponseEntity.ok().body(customerResponseDto);

            }
            Customer savedCustomer = customerRepository.save(customer);
            accountRepository.save(createNewAccount(savedCustomer));

            headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
            headerBase.setResponseMessage(AccountsConstant.MESSAGE_201);
            customerResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(customerResponseDto);

        }catch (Exception exception){
            log.info("error: " + exception.getMessage());

            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            headerBase.setResponseCode(ResponseCodes.FAILED_CODE);
            customerResponseDto.setHeaderBase(headerBase);

            log.info("Response {}", customerResponseDto );
            return ResponseEntity.badRequest().body(customerResponseDto);
        }

    }

    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
       long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
       newAccount.setAccountNumber(randomAccNumber);
       newAccount.setAccountType(AccountsConstant.SAVINGS);
       newAccount.setBranchAddress(AccountsConstant.ADDRESS);
       return newAccount;
    }

    @Override
    public ResponseEntity<FetchCustomerResponseDto> fetchAccount(CustomerDto customerDto) {
        FetchCustomerResponseDto responseDto = new FetchCustomerResponseDto();
        HeaderBase headerBase = new HeaderBase();

        try {
            Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
            Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());

            responseDto.setEmail(customer.getEmail());
            responseDto.setMobileNumber(customer.getMobileNumber());
            responseDto.setName(customer.getName());
            responseDto.setAccountsDto(getAccount(accounts));

            return ResponseEntity.ok().body(responseDto);
        }catch (Exception exception){
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            responseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(responseDto);
        }
    }
    private AccountsDto getAccount(Accounts accounts){
        AccountsDto newAccount = new AccountsDto();
        newAccount.setAccountNumber(accounts.getAccountNumber());
        newAccount.setAccountType(accounts.getAccountType());
        newAccount.setBranchAddress(accounts.getBranchAddress());
        return newAccount;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        return false;
    }

    @Override
    public ResponseEntity<CustomerResponseDto> deleteAccount(CustomerDto customerDto) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        HeaderBase headerBase = new HeaderBase();

        try {
            Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
                customerRepository.delete(customer);
                headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
                headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE + ", Account with number " + customer.getMobileNumber() + " deleted");
                customerResponseDto.setHeaderBase(headerBase);
                return ResponseEntity.ok(customerResponseDto);

        }catch (Exception exception){
            headerBase.setResponseCode(ResponseCodes.FAILED_CODE);
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            customerResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.badRequest().body(customerResponseDto);
        }
    }
}
