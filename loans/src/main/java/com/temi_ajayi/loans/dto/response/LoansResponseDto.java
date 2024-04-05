package com.temi_ajayi.loans.dto.response;

import com.temi_ajayi.loans.dto.HeaderBase;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class LoansResponseDto {
    private HeaderBase headerBase;
    private String loanId;
}
