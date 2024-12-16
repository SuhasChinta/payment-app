package com.Payment_Gateway.Payment_Gateway.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "card_details")
public class CardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "balance")
    private BigDecimal balance;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Column(name="cvv")
    private String cvv;

//    @Column(name = "user_id")
//    private Long userId;
//
//    public Long getCardId() {
//        return cardId;
//    }
//
//    public void setCardId(Long cardId) {
//        this.cardId = cardId;
//    }

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
// Getters and Setters
}
