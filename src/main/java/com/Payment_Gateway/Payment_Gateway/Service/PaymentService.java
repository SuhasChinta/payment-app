package com.Payment_Gateway.Payment_Gateway.Service;

import com.Payment_Gateway.Payment_Gateway.Model.CardDetails;
import com.Payment_Gateway.Payment_Gateway.Model.Orders;
import com.Payment_Gateway.Payment_Gateway.Model.TransactionStatusResponse;
import com.Payment_Gateway.Payment_Gateway.Repo.CardDetailsRepository;
import com.Payment_Gateway.Payment_Gateway.Repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private CardDetailsRepository cardDetailsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Transactional
    public Orders validateAndProcessPayment(String transactionId, String cardNumber, BigDecimal amount, String cvv, String productID, String username) {
        // Find the card by number
        CardDetails cardDetails = cardDetailsRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        // Validate payment amount
        String status = "SUCCESS";

        if (cardDetails.getCardNumber().length() < 16) {
            status = "FAILURE";
        }

        // Deduct amount from card balance
        cardDetails.setBalance(cardDetails.getBalance().subtract(amount));
        cardDetailsRepository.save(cardDetails);

        Orders order = new Orders();
        order.setProductId(productID);
        order.setTransactionID(transactionId);
        order.setAmount(amount);
        order.setUsername(username);
        order.setCardNumber(cardNumber);
        order.setCvv(cvv);

        if (cardDetails.getBalance().compareTo(amount) <= 0 || amount.intValue() <= 0) {
            order.setStatus("FAILURE");
        } else {
            order.setStatus("SUCCESS");
        }

        // Save order details
        ordersRepository.save(order);

        return order;
    }

    // Method to get order details by transaction ID
    @Transactional
    public Map<String, Object> getOrderDetails(String transactionId) {
        // Find the order by transactionId
        Orders order = ordersRepository.findBytransactionID(transactionId);

        // Check if the order is not found
        if (order == null) {
            return Map.of("error", "Order not found");
        }

        // Return relevant order details in a map
        return Map.of(
                "transactionId", order.getTransactionID(),
                "amount", order.getAmount(),
                "status", order.getStatus(),
                "username", order.getUsername(),
                "cardNumber", order.getCardNumber()
        );
    }

    // New method to get transaction status based on transaction ID
    @Transactional
    public TransactionStatusResponse getTransactionStatus(String transactionId) {
        // Find the order by transactionId
        Orders order = ordersRepository.findBytransactionID(transactionId);

        // Check if the order is not found
        if (order == null) {
            return new TransactionStatusResponse("Transaction not found", "failed");
        }

        // Return the transaction status
        return new TransactionStatusResponse(order.getStatus(), "success");
    }
}
