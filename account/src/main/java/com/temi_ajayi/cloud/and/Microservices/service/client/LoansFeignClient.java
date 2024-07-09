package com.temi_ajayi.cloud.and.Microservices.service.client;

import com.temi_ajayi.cloud.and.Microservices.dto.request.CardsRequestDto;
import com.temi_ajayi.cloud.and.Microservices.dto.request.LoansRequestDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCardsResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchLoansResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("loans")
public interface LoansFeignClient {

    @PostMapping("/api/v1/fetch")
    ResponseEntity<FetchLoansResponseDto> fetchLoanDetails(@RequestBody LoansRequestDto loansRequestDto) ;

}
