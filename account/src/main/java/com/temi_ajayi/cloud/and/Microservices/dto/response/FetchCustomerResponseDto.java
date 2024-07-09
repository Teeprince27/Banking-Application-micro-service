package com.temi_ajayi.cloud.and.Microservices.dto.response;

import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import com.temi_ajayi.cloud.and.Microservices.dto.request.AccountResponseDto;
import lombok.Data;

@Data
public class FetchCustomerResponseDto {
    private HeaderBase headerBase;
    private String name;
    private String email;
    private String mobileNumber;
    private AccountResponseDto accountResponseDto;
}
