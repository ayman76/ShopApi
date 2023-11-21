package com.example.shopapi.service;

import com.example.shopapi.dto.BillingAddressDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.model.Customer;
import com.example.shopapi.model.BillingAddress;
import com.example.shopapi.repository.BillingAddressRepository;
import com.example.shopapi.service.impl.BillingAddressServiceImpl;
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
class BillingAddressServiceTest {

    @Mock
    private BillingAddressRepository billingAddressRepository;
    @InjectMocks
    private BillingAddressServiceImpl billingAddressService;

    private BillingAddress billingAddress;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        billingAddressService = new BillingAddressServiceImpl(billingAddressRepository, modelMapper);
        Customer customer = Customer.builder().customerId(1).firstName("Ayman").lastName("Mohamed").phoneNumber("0123456789").appUser(new AppUser()).build();
        billingAddress = BillingAddress.builder().billingAddressId(1).address("321 Cedar Lane").country("Country E").city("Othercity").state("EFG").zipcode("13579").customer(customer).build();
    }


    @Test
    public void BillingAddressService_GetBillingAddressByCustomerId_ReturnBillingAddressDto() {
        when(billingAddressRepository.findByCustomer_CustomerId(Mockito.anyInt())).thenReturn(Optional.ofNullable(billingAddress));

        BillingAddressDto foundedBillingAddress = billingAddressService.getBillingAddressByCustomerId(1);

        assertThat(foundedBillingAddress).isNotNull();
        assertThat(foundedBillingAddress.getBillingAddressId()).isEqualTo(1);
    }

    @Test
    public void BillingAddressService_GetBillingAddressByNotFoundedCustomerId_ThrowsResourceNotFoundedException() {
        when(billingAddressRepository.findByCustomer_CustomerId(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> billingAddressService.getBillingAddressByCustomerId(1));
    }


}