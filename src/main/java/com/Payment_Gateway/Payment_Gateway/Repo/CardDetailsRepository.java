package com.Payment_Gateway.Payment_Gateway.Repo;

import com.Payment_Gateway.Payment_Gateway.Model.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {
    Optional<CardDetails> findByCardNumber(String cardNumber);
}