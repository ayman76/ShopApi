package com.example.shopapi.service;

import com.example.shopapi.dto.ShippingAddressDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.model.Customer;
import com.example.shopapi.model.ShippingAddress;
import com.example.shopapi.repository.ShippingAddressRepository;
import com.example.shopapi.service.impl.ShippingAddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShippingAddressServiceTest {

    @Mock
    private ShippingAddressRepository shippingAddressRepository;
    @InjectMocks
    private ShippingAddressServiceImpl shippingAddressService;

    private ShippingAddress shippingAddress;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        shippingAddressService = new ShippingAddressServiceImpl(shippingAddressRepository, modelMapper);
        Customer customer = Customer.builder().customerId(1).firstName("Ayman").lastName("Mohamed").phoneNumber("0123456789").appUser(new AppUser()).build();
        shippingAddress = ShippingAddress.builder().shippingAddressId(1).address("321 Cedar Lane").country("Country E").city("Othercity").state("EFG").zipcode("13579").customer(customer).build();
    }


    @Test
    public void ShippingAddressService_GetShippingAddressByCustomerId_ReturnShippingAddressDto() {
        when(shippingAddressRepository.findByCustomer_CustomerId(Mockito.anyInt())).thenReturn(Optional.ofNullable(shippingAddress));

        ShippingAddressDto foundedShippingAddress = shippingAddressService.getShippingAddressByCustomerId(1);

        assertThat(foundedShippingAddress).isNotNull();
        assertThat(foundedShippingAddress.getShippingAddressId()).isEqualTo(1);
    }

    @Test
    public void ShippingAddressService_GetShippingAddressByNotFoundedCustomerId_ThrowsResourceNotFoundedException() {
        when(shippingAddressRepository.findByCustomer_CustomerId(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> shippingAddressService.getShippingAddressByCustomerId(1));
    }


}