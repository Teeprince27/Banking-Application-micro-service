package com.temi_ajayi.cloud.and.Microservices.service.client;

import com.temi_ajayi.cloud.and.Microservices.dto.request.CardsRequestDto;
import com.temi_ajayi.cloud.and.Microservices.dto.response.FetchCardsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("cards")
public interface CardsFeignClient {

    @PostMapping("/api/v1/fetch")
    public ResponseEntity<FetchCardsResponseDto> fetchCard(@RequestBody CardsRequestDto cardsRequestDto);


}
