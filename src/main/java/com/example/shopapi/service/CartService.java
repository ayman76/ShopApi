package com.example.shopapi.service;

import com.example.shopapi.dto.CartDto;

public interface CartService {

    CartDto getCartByCustomerId(int id);
}
