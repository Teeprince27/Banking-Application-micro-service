package com.temi_ajayi.cloud.and.Microservices.repository;

import com.temi_ajayi.cloud.and.Microservices.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findFirstByMobileNumber(String mobileNumber);

    Customer findByMobileNumber(String mobileNumber);

    boolean existsByMobileNumber(String mobileNumber);
}
