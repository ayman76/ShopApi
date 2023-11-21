package com.example.shopapi.service.impl;

import com.example.shopapi.dto.BillingAddressDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.BillingAddress;
import com.example.shopapi.repository.BillingAddressRepository;
import com.example.shopapi.service.BillingAddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingAddressServiceImpl implements BillingAddressService {
    private final BillingAddressRepository billingAddressRepository;
    private final ModelMapper modelMapper;

    @Override
    public BillingAddressDto getBillingAddressByCustomerId(int id) {
        BillingAddress billingAddress = billingAddressRepository.findByCustomer_CustomerId(id).orElseThrow(() -> new ResourceNotFoundException("Not founded billing address for customer with id: " + id));
        return modelMapper.map(billingAddress, BillingAddressDto.class);
    }
}
