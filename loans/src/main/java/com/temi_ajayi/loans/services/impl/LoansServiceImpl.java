package com.temi_ajayi.loans.services.impl;

import com.temi_ajayi.loans.constants.LoansConstants;
import com.temi_ajayi.loans.dto.HeaderBase;
import com.temi_ajayi.loans.dto.request.LoansRequestDto;
import com.temi_ajayi.loans.dto.response.FetchLoansResponseDto;
import com.temi_ajayi.loans.dto.response.LoansResponseDto;
import com.temi_ajayi.loans.model.Loans;
import com.temi_ajayi.loans.repository.LoansRepository;
import com.temi_ajayi.loans.services.ILoansServices;
import com.temi_ajayi.loans.utils.ResponseCodes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class LoansServiceImpl implements ILoansServices {

    private final LoansRepository loansRepository;
    @Override
    public ResponseEntity<LoansResponseDto> createLoan(LoansRequestDto loansRequestDto) {

        LoansResponseDto loansResponseDto = new LoansResponseDto();
        HeaderBase headerBase = new HeaderBase();
        try{
            boolean loanExist = loansRepository.existsByMobileNumber(loansRequestDto.getMobileNumber());
            log.info("checking if customer exist::: {}", loanExist);
            if(loanExist){
                headerBase.setResponseCode(ResponseCodes.ALREADY_EXIST_CODE);
                headerBase.setResponseMessage(ResponseCodes.ALREADY_EXIST_MESSAGE + loansRequestDto.getMobileNumber());
                loansResponseDto.setHeaderBase(headerBase);
                return ResponseEntity.ok().body(loansResponseDto);

            }
            Loans savedLoans = loansRepository.save(createNewLoan(loansRequestDto));

            headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
            headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE);
            loansResponseDto.setHeaderBase(headerBase);
            loansResponseDto.setLoanId(savedLoans.getLoanId().toString());
            return ResponseEntity.ok().body(loansResponseDto);

        }catch (Exception exception){
            log.info("error: " + exception.getMessage());

            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            headerBase.setResponseCode(ResponseCodes.FAILED_CODE);
            loansResponseDto.setHeaderBase(headerBase);

            log.info("Response {}", loansResponseDto );
            return ResponseEntity.badRequest().body(loansResponseDto);
        }
    }

    private Loans createNewLoan(LoansRequestDto loansRequestDto) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(loansRequestDto.getMobileNumber());
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public ResponseEntity<FetchLoansResponseDto> fetchLoan(LoansRequestDto loansRequestDto) {
        FetchLoansResponseDto responseDto = new FetchLoansResponseDto();
        HeaderBase headerBase = new HeaderBase();

        try {
            Loans loans = loansRepository.findByMobileNumber(loansRequestDto.getMobileNumber());

            responseDto.setLoanType(loans.getLoanType());
            responseDto.setLoanNumber(loans.getLoanNumber());
            responseDto.setMobileNumber(loans.getMobileNumber());
            responseDto.setTotalLoan(loans.getTotalLoan());
            responseDto.setAmountPaid(loans.getAmountPaid());
            responseDto.setOutstandingAmount(loans.getOutstandingAmount());

            headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE);
            headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
            responseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(responseDto);
        }catch (Exception exception){
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            responseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(responseDto);
        }
    }

    @Override
    public ResponseEntity<LoansResponseDto> updateLoan(LoansRequestDto loansRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<LoansResponseDto> deleteLoan(LoansRequestDto loansRequestDto) {
            LoansResponseDto loansResponseDto = new LoansResponseDto();
            HeaderBase headerBase = new HeaderBase();

        try {
            Loans cards = loansRepository.findByMobileNumber(loansRequestDto.getMobileNumber());
            loansRepository.delete(cards);
            headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
            headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE + ", Loan with number " + cards.getMobileNumber() + " deleted");
            loansResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok(loansResponseDto);

        }catch (Exception exception){
            headerBase.setResponseCode(ResponseCodes.DOES_NOT_EXIST_CODE);
            headerBase.setResponseMessage(ResponseCodes.DOES_NOT_EXIST_MESSAGE);
            loansResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.badRequest().body(loansResponseDto);
        }
    }
}
