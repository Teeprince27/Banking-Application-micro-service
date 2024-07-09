package com.temi_ajayi.cloud.and.Microservices.dto.request;

import lombok.Data;

@Data

public class AccountResponseDto {


    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
