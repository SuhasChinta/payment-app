package com.Payment_Gateway.Payment_Gateway.Controller;

import com.Payment_Gateway.Payment_Gateway.Model.Orders;
import com.Payment_Gateway.Payment_Gateway.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Payment_Gateway.Payment_Gateway.Model.TransactionStatusResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<Map<String, Orders>> processPayment(@RequestBody Orders request) {
        String productID = request.getProductId();
        String cardNumber = request.getCardNumber();
        BigDecimal amount = new BigDecimal(String.valueOf(request.getAmount()));
        String transactionID = UUID.randomUUID().toString();
        String cvv = request.getCvv();
        String username = request.getUsername();

        Orders result = paymentService.validateAndProcessPayment(transactionID, cardNumber, amount, cvv, productID, username);
        return ResponseEntity.ok(Map.of("message", result));
    }

    // Existing endpoint to get order details based on transactionId
    @GetMapping("/getdetails/{transactionId}")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable String transactionId) {
        Map<String, Object> orderDetails = paymentService.getOrderDetails(transactionId);

        // If order is not found
        if (orderDetails.containsKey("error")) {
            return ResponseEntity.status(404).body(orderDetails);  // Return 404 if not found
        }

        // Return 200 OK if order details are found
        return ResponseEntity.ok(orderDetails);
    }

    // New endpoint to check the transaction status based on transactionId
//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping("/status/{transactionId}")
//    public ResponseEntity<TransactionStatusResponse> checkStatus(@PathVariable String transactionId) {
//        // Call service method to fetch transaction status
//        TransactionStatusResponse status = paymentService.getTransactionStatus(transactionId);
//
//        // If the status is not found or any error occurs
//        if (status == null) {
//            return ResponseEntity.status(404).body(new TransactionStatusResponse("Transaction not found", "failed"));
//        }
//
//        // Return the transaction status as a response
//        return ResponseEntity.ok(status);
//    }
}
