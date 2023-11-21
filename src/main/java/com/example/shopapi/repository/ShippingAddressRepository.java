package com.example.shopapi.repository;

import com.example.shopapi.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {
    Optional<ShippingAddress> findByCustomer_CustomerId(int id);
}
