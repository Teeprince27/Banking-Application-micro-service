package com.temi_ajayi.cloud.and.Microservices.dto.response;

import com.temi_ajayi.cloud.and.Microservices.dto.HeaderBase;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomerResponseDto {
    private HeaderBase headerBase;
    private String mobileNumber;
}
