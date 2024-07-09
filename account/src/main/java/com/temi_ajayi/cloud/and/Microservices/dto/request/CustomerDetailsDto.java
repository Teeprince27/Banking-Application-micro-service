package com.temi_ajayi.cloud.and.Microservices.dto.request;

import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCardsResponseDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchLoansResponseDto;
import lombok.Data;

@Data
public class CustomerDetailsDto {

    private HeaderBase headerBase;
    private String name;


    private String email;


    private String mobileNumber;

    private AccountResponseDto accountResponseDto;

    private FetchLoansResponseDto loansResponseDtoDto;

    private FetchCardsResponseDto cardsResponseDto;
}
