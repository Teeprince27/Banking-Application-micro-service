package com.temi_ajayi.cloud.and.Microservices.service.impl;

import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import com.temi_ajayi.cloud.and.Microservices.dto.request.*;
import com.temi_ajayi.cloud.and.Microservices.dto.request.AccountResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCardsResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchLoansResponseDto;
import com.temi_ajayi.cloud.and.Microservices.model.Accounts;
import com.temi_ajayi.cloud.and.Microservices.model.Customer;
import com.temi_ajayi.cloud.and.Microservices.repository.AccountRepository;
import com.temi_ajayi.cloud.and.Microservices.repository.CustomerRepository;
import com.temi_ajayi.cloud.and.Microservices.service.ICustomerService;
import com.temi_ajayi.cloud.and.Microservices.service.client.CardsFeignClient;
import com.temi_ajayi.cloud.and.Microservices.service.client.LoansFeignClient;
import com.temi_ajayi.cloud.and.Microservices.utils.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements ICustomerService {


    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    @Override
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(CustomerDetailRequestDto customerDetailRequestDto) {
        log.info("Inside fetch customer details ");
        CustomerDetailsDto responseDto = new CustomerDetailsDto();
        HeaderBase headerBase = new HeaderBase();

        boolean checkCustomer = customerRepository.existsByMobileNumber(customerDetailRequestDto.getMobileNumber());
        log.info("Customer Details from post man{}", customerDetailRequestDto.getMobileNumber());
        log.info("Customer Details  available {}", checkCustomer);
        if(!checkCustomer){
            headerBase.setResponseCode(ResponseCodes.DOES_NOT_EXIST_CODE);
            headerBase.setResponseMessage("Customer with Mobile number  " + customerDetailRequestDto.getMobileNumber() + " "+ ResponseCodes.NOT_FOUND_MESSAGE);
            responseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(responseDto);
        }else {

            try {
                Customer customer = customerRepository.findByMobileNumber(customerDetailRequestDto.getMobileNumber());
                log.info("mobile number {}", customer);
                Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
                log.info("customer id {}", accounts);

                AccountResponseDto accountResponseDto = new AccountResponseDto();
                accountResponseDto.setAccountNumber(accounts.getAccountNumber());
                accountResponseDto.setAccountType(accounts.getAccountType());
                accountResponseDto.setBranchAddress(accounts.getBranchAddress());

                log.info("gotten account details {}", accountResponseDto);

                // Cards Request Dto
                CardsRequestDto cardsRequestDto = new CardsRequestDto();
                cardsRequestDto.setMobileNumber(customerDetailRequestDto.getMobileNumber());

                // Loans Request Dto
                LoansRequestDto loansRequestDto = new LoansRequestDto();
                loansRequestDto.setMobileNumber(customerDetailRequestDto.getMobileNumber());



                //Loans Details from another service
                log.info("want to call loans feign client");
                ResponseEntity<FetchLoansResponseDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(loansRequestDto);
                log.info("loans details {}", loansDtoResponseEntity);

                //Cards Details from another service
                log.info("want to call cards feign client");

                ResponseEntity<FetchCardsResponseDto> cardsDtoResponseEntity = cardsFeignClient.fetchCard(cardsRequestDto);
                log.info("cards details {}", cardsDtoResponseEntity);

                //Header Base
                headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
                headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE);



                responseDto.setName(customer.getName());
                responseDto.setEmail(customer.getEmail());
                responseDto.setMobileNumber(customer.getMobileNumber());
                responseDto.setAccountResponseDto(accountResponseDto);
                responseDto.setLoansResponseDtoDto(loansDtoResponseEntity.getBody());
                responseDto.setCardsResponseDto(cardsDtoResponseEntity.getBody());
                responseDto.setHeaderBase(headerBase);

                return ResponseEntity.ok().body(responseDto);
            }catch (Exception exception){
                headerBase.setResponseCode(ResponseCodes.FAILED_CODE);
                headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
                responseDto.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(responseDto);
            }

        }
    }
}
