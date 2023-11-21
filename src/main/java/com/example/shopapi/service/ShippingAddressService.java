package com.example.shopapi.service;

import com.example.shopapi.dto.ShippingAddressDto;

public interface ShippingAddressService {

    ShippingAddressDto getShippingAddressByCustomerId(int id);
}
