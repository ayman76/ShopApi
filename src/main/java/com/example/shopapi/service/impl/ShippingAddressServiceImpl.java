package com.example.shopapi.service.impl;

import com.example.shopapi.dto.ShippingAddressDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.ShippingAddress;
import com.example.shopapi.repository.ShippingAddressRepository;
import com.example.shopapi.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {
    private final ShippingAddressRepository shippingAddressRepository;
    private final ModelMapper modelMapper;

    @Override
    public ShippingAddressDto getShippingAddressByCustomerId(int id) {
        ShippingAddress shippingAddress = shippingAddressRepository.findByCustomer_CustomerId(id).orElseThrow(() -> new ResourceNotFoundException("Not founded shipping address for customer with id: " + id));
        return modelMapper.map(shippingAddress, ShippingAddressDto.class);
    }
}
