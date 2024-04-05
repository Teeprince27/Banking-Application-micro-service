package com.temi_ajayi.loans.services;

import com.temi_ajayi.loans.dto.request.LoansRequestDto;
import com.temi_ajayi.loans.dto.response.FetchLoansResponseDto;
import com.temi_ajayi.loans.dto.response.LoansResponseDto;
import org.springframework.http.ResponseEntity;

public interface ILoansServices {
    ResponseEntity<LoansResponseDto> createLoan(LoansRequestDto loansRequestDto);
    ResponseEntity<FetchLoansResponseDto> fetchLoan(LoansRequestDto loansRequestDto);
    ResponseEntity<LoansResponseDto> updateLoan(LoansRequestDto loansRequestDto);
    ResponseEntity<LoansResponseDto> deleteLoan(LoansRequestDto loansRequestDto);
}
