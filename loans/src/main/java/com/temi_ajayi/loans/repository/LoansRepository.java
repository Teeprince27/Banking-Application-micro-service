package com.temi_ajayi.loans.repository;

import com.temi_ajayi.loans.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, Long> {
    Loans findByMobileNumber(String mobileNumber);

    boolean existsByMobileNumber (String mobileNumber);

    Optional<Loans> findByLoanNumber(String loanNumber);

    Optional<Loans> findFirstByMobileNumber(String mobileNumber);
}
