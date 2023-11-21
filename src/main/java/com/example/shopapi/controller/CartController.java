package com.example.shopapi.controller;

import com.example.shopapi.dto.CartDto;
import com.example.shopapi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("api/v1/customer/{customerId}/cart")
    public ResponseEntity<CartDto> getCartByCustomerId(@PathVariable int customerId) {
        return new ResponseEntity<>(cartService.getCartByCustomerId(customerId), HttpStatus.OK);
    }
}
