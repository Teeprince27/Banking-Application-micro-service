package com.temi_ajayi.cards.services.impl;

import com.temi_ajayi.cards.constants.CardsConstants;
import com.temi_ajayi.cards.dto.HeaderBase;
import com.temi_ajayi.cards.dto.request.CardsRequestDto;
import com.temi_ajayi.cards.dto.response.CardsResponseDto;
import com.temi_ajayi.cards.dto.response.FetchCardsResponseDto;
import com.temi_ajayi.cards.model.Cards;
import com.temi_ajayi.cards.repository.CardsRepository;
import com.temi_ajayi.cards.services.ICardsServices;
import com.temi_ajayi.cards.utils.ResponseCodes;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServicesImpl implements ICardsServices {
    private final CardsRepository cardsRepository;

    @Override
    public ResponseEntity<CardsResponseDto> createCard(CardsRequestDto cardsRequestDto) {
        CardsResponseDto cardsResponseDto = new CardsResponseDto();
        HeaderBase headerBase = new HeaderBase();
        try{

            Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(cardsRequestDto.getMobileNumber());

            if(optionalCards.isPresent()){
                headerBase.setResponseCode(ResponseCodes.ALREADY_EXIST_CODE);
                headerBase.setResponseMessage(ResponseCodes.ALREADY_EXIST_MESSAGE + cardsRequestDto.getMobileNumber());
                cardsResponseDto.setHeaderBase(headerBase);
                return ResponseEntity.badRequest().body(cardsResponseDto);
            }else {
                cardsRepository.save(createNewCard(cardsRequestDto));
                headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
                headerBase.setResponseMessage(ResponseCodes.SUCCESS_CODE);
                cardsResponseDto.setHeaderBase(headerBase);
                return ResponseEntity.ok().body(cardsResponseDto);

            }
        }catch (Exception exception){
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            headerBase.setResponseCode(ResponseCodes.FAILED_CODE);
            cardsResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(cardsResponseDto);
        }
    }

    private Cards createNewCard(CardsRequestDto cardsRequestDto) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(cardsRequestDto.getMobileNumber());
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
   public ResponseEntity<FetchCardsResponseDto> fetchCard(CardsRequestDto cardsRequestDto){
        FetchCardsResponseDto cardsResponseDto = new FetchCardsResponseDto();
        HeaderBase headerBase = new HeaderBase();
        try{

            Cards cards = cardsRepository.findAllByMobileNumber(cardsRequestDto.getMobileNumber());

            cardsResponseDto.setCardNumber(cards.getCardNumber());
            cardsResponseDto.setCardType(cards.getCardType());
            cardsResponseDto.setAmountUsed(cards.getAmountUsed());
            cardsResponseDto.setTotalLimit(cards.getTotalLimit());
            cardsResponseDto.setAvailableAmount(cards.getAvailableAmount());
            cardsResponseDto.setMobileNumber(cards.getMobileNumber());

            headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
            headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE);
            cardsResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(cardsResponseDto);
        }catch (Exception exception){
            headerBase.setResponseCode(ResponseCodes.FAILED_CODE);
            headerBase.setResponseMessage(ResponseCodes.FAILED_MESSAGE);
            cardsResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok().body(cardsResponseDto);
        }
    }

    @Override
   public ResponseEntity<CardsResponseDto> updateCard (CardsRequestDto cardsRequestDto) {
        CardsResponseDto cardsResponseDto = new CardsResponseDto();
        HeaderBase headerBase = new HeaderBase();
        try{

            Cards cards = cardsRepository.findAllByCardNumber(cardsRequestDto.getCardNumber());

            cards.setCardNumber(cardsRequestDto.getMobileNumber());
            cardsRepository.save(cards);

            return ResponseEntity.ok().body(cardsResponseDto);
        }catch (Exception exception){
            return ResponseEntity.ok().body(cardsResponseDto);
        }
    }

    @Override
    public ResponseEntity<CardsResponseDto> deleteCard(CardsRequestDto cardsRequestDto) {
        CardsResponseDto cardsResponseDto = new CardsResponseDto();
        HeaderBase headerBase = new HeaderBase();
        try {
            Cards cards = cardsRepository.findAllByMobileNumber(cardsRequestDto.getMobileNumber());
            cardsRepository.delete(cards);
            headerBase.setResponseCode(ResponseCodes.SUCCESS_CODE);
            headerBase.setResponseMessage(ResponseCodes.SUCCESS_MESSAGE + ", Card with number " + cards.getMobileNumber() + " deleted");
            cardsResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.ok(cardsResponseDto);

        }catch (Exception exception){
            headerBase.setResponseCode(ResponseCodes.DOES_NOT_EXIST_CODE);
            headerBase.setResponseMessage(ResponseCodes.DOES_NOT_EXIST_MESSAGE);
            cardsResponseDto.setHeaderBase(headerBase);
            return ResponseEntity.badRequest().body(cardsResponseDto);
        }
    }
}
