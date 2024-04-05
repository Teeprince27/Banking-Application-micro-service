package com.temi_ajayi.cards.controller;

import com.temi_ajayi.cards.dto.request.CardsContactInfoDto;
import com.temi_ajayi.cards.dto.request.CardsRequestDto;
import com.temi_ajayi.cards.dto.response.CardsResponseDto;
import com.temi_ajayi.cards.dto.response.FetchCardsResponseDto;
import com.temi_ajayi.cards.repository.CardsRepository;
import com.temi_ajayi.cards.services.ICardsServices;
import com.temi_ajayi.cards.services.impl.CardsServicesImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardsController {


    private final ICardsServices cardsServices;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    public CardsController(ICardsServices cardsServices){
        this.cardsServices = cardsServices;
    }

    @PostMapping("/create")
    public ResponseEntity<CardsResponseDto> createCards(@RequestBody CardsRequestDto cardsRequestDto){
        return cardsServices.createCard(cardsRequestDto);
    }

    @PostMapping("/fetch")
    public ResponseEntity<FetchCardsResponseDto> fetchCard(@RequestBody CardsRequestDto cardsRequestDto){
        return cardsServices.fetchCard(cardsRequestDto);
    }



    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

//    @GetMapping("/java-version")
//    public ResponseEntity<String> getJavaVersion(){
//        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
//    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        log.info("Contact:::{} ", cardsContactInfoDto.getContactDetails());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);

    }
}
