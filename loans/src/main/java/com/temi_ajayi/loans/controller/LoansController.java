package com.temi_ajayi.loans.controller;

import com.temi_ajayi.loans.dto.request.LoansContactInfoDto;
import com.temi_ajayi.loans.dto.request.LoansRequestDto;
import com.temi_ajayi.loans.dto.response.FetchLoansResponseDto;
import com.temi_ajayi.loans.dto.response.LoansResponseDto;
import com.temi_ajayi.loans.services.ILoansServices;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Slf4j
public class LoansController {

    public LoansController(ILoansServices loansService){
        this.iLoansService = loansService;
    }
    private ILoansServices iLoansService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;



    @PostMapping("/create")
    public ResponseEntity<LoansResponseDto> createLoan(@RequestBody LoansRequestDto loansRequestDto) {
       return iLoansService.createLoan(loansRequestDto);
    }


    @PostMapping("/fetch")
    public ResponseEntity<FetchLoansResponseDto> fetchLoanDetails(@RequestBody LoansRequestDto loansRequestDto) {
        return iLoansService.fetchLoan(loansRequestDto);

    }

//    @PutMapping("/update")
//    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
//        boolean isUpdated = iLoansService.updateLoan(loansDto);
//    }
//
    @DeleteMapping("/delete")
    public ResponseEntity<LoansResponseDto> deleteLoanDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         LoansRequestDto loansRequestDto) {
       return iLoansService.deleteLoan(loansRequestDto);

    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }


    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        log.info("Contact:::{} ", loansContactInfoDto.getContactDetails());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDto);

    }
}
