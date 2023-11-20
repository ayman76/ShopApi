package com.example.shopapi.service;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.CustomerDto;
import com.example.shopapi.dto.ShippingAddressDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.model.Customer;
import com.example.shopapi.model.ShippingAddress;
import com.example.shopapi.repository.CustomerRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShippingAddressServiceTest {

    @Mock
    private ShippingAddressRepository shippingAddressRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ShippingAddressServiceImpl shippingAddressService;

    private Customer customer;
    private ShippingAddress shippingAddress;
    private ShippingAddressDto shippingAddressDto;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        shippingAddressService = new ShippingAddressServiceImpl(shippingAddressRepository, customerRepository, modelMapper);
        customer = Customer.builder().customerId(1).firstName("Ayman").lastName("Mohamed").phoneNumber("0123456789").appUser(new AppUser()).build();
        shippingAddress = ShippingAddress.builder().shippingAddressId(1).address("321 Cedar Lane").country("Country E").city("Othercity").state("EFG").zipcode("13579").customer(customer).build();
        shippingAddressDto = ShippingAddressDto.builder().address("321 Cedar Lane").country("Country E").city("Othercity").state("EFG").zipcode("13579").customer(modelMapper.map(customer, CustomerDto.class)).build();
    }

    @Test
    public void ShippingAddressService_CreateShippingAddress_ReturnShippingAddressDto() {
        when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(customer));
        when(shippingAddressRepository.save(Mockito.any(ShippingAddress.class))).thenReturn(shippingAddress);

        ShippingAddressDto savedShippingAddress = shippingAddressService.createShippingAddress(shippingAddressDto);

        assertThat(savedShippingAddress).isNotNull();
    }

    @Test
    public void ShippingAddressService_CreateShippingAddressNotFoundedCustomer_ThrowsResourceNotFoundedException() {
        when(customerRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> shippingAddressService.createShippingAddress(shippingAddressDto));
    }

    @Test
    public void ShippingAddressService_GetAllShippingAddress_ReturnApiResponse() {
        Page<ShippingAddress> shippingAddresses = Mockito.mock(Page.class);
        when(shippingAddressRepository.findAll(Mockito.any(Pageable.class))).thenReturn(shippingAddresses);

        ApiResponse<ShippingAddressDto> response = shippingAddressService.getAllShippingAddress(0, 10);

        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotNull();
    }

    @Test
    public void ShippingAddressService_GetShippingAddressById_ReturnShippingAddressDto() {
        when(shippingAddressRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(shippingAddress));

        ShippingAddressDto foundedShippingAddress = shippingAddressService.getShippingAddressById(1);

        assertThat(foundedShippingAddress).isNotNull();
        assertThat(foundedShippingAddress.getShippingAddressId()).isEqualTo(1);
    }

    @Test
    public void ShippingAddressService_GetShippingAddressWithNotFoundedId_ThrowsResourceNotFoundedException() {
        when(shippingAddressRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> shippingAddressService.getShippingAddressById(1));
    }

    @Test
    public void ShippingAddressService_UpdateShippingAddress_ReturnShippingAddressDto() {
        when(shippingAddressRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(shippingAddress));
        when(shippingAddressRepository.save(Mockito.any(ShippingAddress.class))).thenReturn(shippingAddress);

        ShippingAddressDto updatedShippingAddress = shippingAddressService.updateShippingAddress(1, shippingAddressDto);

        assertThat(updatedShippingAddress).isNotNull();
        assertThat(updatedShippingAddress.getShippingAddressId()).isEqualTo(1);
        assertThat(updatedShippingAddress.getAddress()).isEqualTo(shippingAddressDto.getAddress());
        assertThat(updatedShippingAddress.getCity()).isEqualTo(shippingAddressDto.getCity());
        assertThat(updatedShippingAddress.getState()).isEqualTo(shippingAddressDto.getState());
        assertThat(updatedShippingAddress.getCountry()).isEqualTo(shippingAddressDto.getCountry());
        assertThat(updatedShippingAddress.getZipcode()).isEqualTo(shippingAddressDto.getZipcode());
    }

    @Test
    public void ShippingAddressService_UpdateShippingAddressWithNotFoundedId_ThrowsResourceNotFoundedException() {
        when(shippingAddressRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> shippingAddressService.updateShippingAddress(1, shippingAddressDto));
    }

    @Test
    public void ShippingAddressService_DeleteShippingAddress_ReturnVoid() {
        when(shippingAddressRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(shippingAddress));
        shippingAddressService.deleteShippingAddress(1);
        verify(shippingAddressRepository, times(1)).findById(1);
        verify(shippingAddressRepository, times(1)).delete(shippingAddress);
    }

    @Test
    public void ShippingAddressService_DeleteShippingAddressWithNotFoundedId_ThrowsResourceNotFoundedException() {
        when(shippingAddressRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> shippingAddressService.deleteShippingAddress(1));
    }


}