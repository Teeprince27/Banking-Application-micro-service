package com.temi_ajayi.cloud.and.Microservices.dto.response;


import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import lombok.Data;

@Data
public class FetchLoansResponseDto {
//    private HeaderBase headerBase;
    private String mobileNumber;
    private String loanNumber;
    private String loanType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
}
