package com.example.shopapi.controller;

import com.example.shopapi.dto.BillingAddressDto;
import com.example.shopapi.service.BillingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/billingAddress")
public class BillingAddressController {
    private final BillingAddressService billingAddressService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<BillingAddressDto> getBillingAddressByCustomerId(@PathVariable int customerId) {
        return new ResponseEntity<>(billingAddressService.getBillingAddressByCustomerId(customerId), HttpStatus.OK);
    }
}
