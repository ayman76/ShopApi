package com.example.shopapi.repository;

import com.example.shopapi.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Integer> {
    Optional<BillingAddress> findByCustomer_CustomerId(int id);
}
