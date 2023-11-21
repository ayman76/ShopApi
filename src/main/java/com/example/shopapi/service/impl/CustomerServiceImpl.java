package com.example.shopapi.service.impl;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.CustomerDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.*;
import com.example.shopapi.repository.AppUserRepository;
import com.example.shopapi.repository.CustomerRepository;
import com.example.shopapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.shopapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        AppUser appUser = appUserRepository.findByEmail(customer.getAppUser().getEmail()).orElseThrow(() -> new ResourceNotFoundException("Not Founded User with emailL: " + customer.getAppUser().getEmail()));
        customer.setAppUser(appUser);
        customer.setBillingAddress(new BillingAddress("", "", "", "", ""));
        customer.setShippingAddress(new ShippingAddress("", "", "", "", ""));
        customer.setCart(new Cart(0.0));

        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public ApiResponse<CustomerDto> getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<Customer> listOfCustomers = customers.getContent();
        List<CustomerDto> content = listOfCustomers.stream().map(c -> modelMapper.map(c, CustomerDto.class)).toList();

        return getApiResponse(pageNo, pageSize, content, customers);
    }

    @Override
    public CustomerDto getCustomerById(int id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Customer with id: " + id));
        return modelMapper.map(foundedCustomer, CustomerDto.class);
    }

    @Override
    public CustomerDto updateCustomer(int id, CustomerDto customerDto) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Customer with id: " + id));

        ShippingAddress shippingAddress = modelMapper.map(customerDto.getShippingAddress(), ShippingAddress.class);
        shippingAddress.setCustomer(foundedCustomer);
        shippingAddress.setCreate_date(foundedCustomer.getShippingAddress().getCreate_date());
        shippingAddress.setLast_update(foundedCustomer.getShippingAddress().getLast_update());
        shippingAddress.setShippingAddressId(foundedCustomer.getShippingAddress().getShippingAddressId());

        BillingAddress billingAddress = modelMapper.map(customerDto.getBillingAddress(), BillingAddress.class);
        billingAddress.setCustomer(foundedCustomer);
        if (foundedCustomer.getBillingAddress() != null) {
            billingAddress.setCreate_date(foundedCustomer.getBillingAddress().getCreate_date());
            billingAddress.setLast_update(foundedCustomer.getBillingAddress().getLast_update());
            billingAddress.setBillingAddressId(foundedCustomer.getBillingAddress().getBillingAddressId());
        }

        foundedCustomer.setFirstName(customerDto.getFirstName());
        foundedCustomer.setLastName(customerDto.getLastName());
        foundedCustomer.setPhoneNumber(customerDto.getPhoneNumber());
        foundedCustomer.setShippingAddress(shippingAddress);
        foundedCustomer.setBillingAddress(billingAddress);

        Customer updatedCustomer = customerRepository.save(foundedCustomer);
        return modelMapper.map(updatedCustomer, CustomerDto.class);
    }

    @Override
    public void deleteCustomer(int id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Customer with id: " + id));
        customerRepository.delete(foundedCustomer);
    }
}
