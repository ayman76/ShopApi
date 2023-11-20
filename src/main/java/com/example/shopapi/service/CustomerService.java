package com.example.shopapi.service;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.CustomerDto;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    ApiResponse<CustomerDto> getAllCustomers(int pageNo, int pageSize);

    CustomerDto getCustomerById(int id);

    CustomerDto updateCustomer(int id, CustomerDto customerDto);

    void deleteCustomer(int id);
}
