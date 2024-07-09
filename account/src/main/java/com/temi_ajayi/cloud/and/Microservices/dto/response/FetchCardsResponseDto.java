package com.temi_ajayi.cloud.and.Microservices.dto.response;


import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import lombok.Data;

@Data
public class FetchCardsResponseDto {
//    private HeaderBase headerBase;
    private String mobileNumber;

    private String cardNumber;

    private String cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;
}
