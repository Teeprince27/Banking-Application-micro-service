package com.temi_ajayi.cards.services;

import com.temi_ajayi.cards.dto.request.CardsRequestDto;
import com.temi_ajayi.cards.dto.response.CardsResponseDto;
import com.temi_ajayi.cards.dto.response.FetchCardsResponseDto;
import org.springframework.http.ResponseEntity;

public interface ICardsServices {

    ResponseEntity<CardsResponseDto> createCard(CardsRequestDto cardsRequestDto);


    ResponseEntity<FetchCardsResponseDto> fetchCard(CardsRequestDto cardsRequestDto);

    ResponseEntity<CardsResponseDto> updateCard(CardsRequestDto cardsRequestDto);

    ResponseEntity<CardsResponseDto> deleteCard(CardsRequestDto cardsRequestDto);
}
