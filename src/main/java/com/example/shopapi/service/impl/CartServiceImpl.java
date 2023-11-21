package com.example.shopapi.service.impl;

import com.example.shopapi.dto.CartDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.Cart;
import com.example.shopapi.repository.CartRepository;
import com.example.shopapi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDto getCartByCustomerId(int id) {
        Cart foundedCart = cartRepository.findByCustomer_CustomerId(id).orElseThrow(() -> new ResourceNotFoundException("Not founded cart for customer with id: " + id));
        return modelMapper.map(foundedCart, CartDto.class);
    }
}
