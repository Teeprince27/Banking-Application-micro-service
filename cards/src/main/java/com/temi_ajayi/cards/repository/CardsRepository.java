package com.temi_ajayi.cards.repository;

import com.temi_ajayi.cards.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Cards, Long> {

    Optional<Cards> findByMobileNumber(String mobileNumber);

    Cards findAllByMobileNumber(String mobileNumber);

    Cards findAllByCardNumber(String cardNumber);
    Optional<Cards> findByCardNumber(String cardNumber);

}
