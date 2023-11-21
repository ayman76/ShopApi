package com.example.shopapi.controller;

import com.example.shopapi.dto.ShippingAddressDto;
import com.example.shopapi.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;

    @GetMapping("/api/v1/customer/{customerId}/shippingAddress")
    public ResponseEntity<ShippingAddressDto> getShippingAddressByCustomerId(@PathVariable int customerId) {
        return new ResponseEntity<>(shippingAddressService.getShippingAddressByCustomerId(customerId), HttpStatus.OK);
    }
}
