package com.temi_ajayi.loans.dto.response;

import com.temi_ajayi.loans.dto.HeaderBase;
import lombok.Data;

@Data
public class FetchLoansResponseDto {
    private HeaderBase headerBase;
    private String mobileNumber;
    private String loanNumber;
    private String loanType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
}
