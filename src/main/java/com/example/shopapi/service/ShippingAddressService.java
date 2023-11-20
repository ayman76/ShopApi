package com.example.shopapi.service;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.ShippingAddressDto;

public interface ShippingAddressService {

    ShippingAddressDto createShippingAddress(ShippingAddressDto shippingAddressDto);

    ApiResponse<ShippingAddressDto> getAllShippingAddress(int pageNo, int pageSize);

    ShippingAddressDto getShippingAddressById(int id);

    ShippingAddressDto updateShippingAddress(int id, ShippingAddressDto shippingAddressDto);

    void deleteShippingAddress(int id);
}
