package com.example.shopapi.service;

import com.example.shopapi.dto.BillingAddressDto;

public interface BillingAddressService {

    BillingAddressDto getBillingAddressByCustomerId(int id);
}
