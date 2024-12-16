package com.Payment_Gateway.Payment_Gateway.Repo;


import com.Payment_Gateway.Payment_Gateway.Model.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
    Orders findBytransactionID(String transactionId);
}
