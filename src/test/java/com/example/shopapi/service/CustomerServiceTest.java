package com.example.shopapi.service;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.AppUserDto;
import com.example.shopapi.dto.CustomerDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.model.Customer;
import com.example.shopapi.repository.AppUserRepository;
import com.example.shopapi.repository.CustomerRepository;
import com.example.shopapi.service.impl.CustomerServiceImpl;
import org.assertj.core.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AppUserRepository appUserRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    private AppUser appUser;
    private Customer customer;
    private CustomerDto customerDto;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        customerService = new CustomerServiceImpl(customerRepository, appUserRepository, modelMapper);
        appUser = AppUser.builder().id(1).email("user@example.com").password("12345678").build();
        customer = Customer.builder().customerId(1).firstName("Ayman").lastName("Mohamed").phoneNumber("0123456789").appUser(appUser).build();
        customerDto = CustomerDto.builder().customerId(1).firstName("Ayman").lastName("Mohamed").phoneNumber("0123456789").appUser(modelMapper.map(appUser, AppUserDto.class)).build();

    }

    @Test
    public void CustomerService_CreateCustomer_ReturnCustomerDto() {
        when(appUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(appUser));
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        CustomerDto savedCustomer = customerService.createCustomer(customerDto);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getFirstName()).isEqualTo(customerDto.getFirstName());
        Assertions.assertThat(savedCustomer.getLastName()).isEqualTo(customerDto.getLastName());
        Assertions.assertThat(savedCustomer.getPhoneNumber()).isEqualTo(customerDto.getPhoneNumber());
        Assertions.assertThat(savedCustomer.getAppUser().getEmail()).isEqualTo(customerDto.getAppUser().getEmail());
    }

    @Test
    public void CustomerService_GetAllCustomers_ReturnApiResponse() {

        Page<Customer> customers = Mockito.mock(Page.class);
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(customers);

        ApiResponse<CustomerDto> response = customerService.getAllCustomers(0, 10);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContent()).isNotNull();
    }

    @Test
    public void CustomerService_GetCustomerById_ReturnCustomerDto() {
        when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(customer));

        CustomerDto foundedCustomer = customerService.getCustomerById(1);

        Assertions.assertThat(foundedCustomer).isNotNull();
        Assertions.assertThat(foundedCustomer.getCustomerId()).isEqualTo(1);

    }

    @Test
    public void CustomerService_GetCustomerByWrongId_ThrowResourceNotFoundException() {
        when(customerRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1));
    }

    @Test
    public void CustomerService_UpdateCustomer_ReturnCustomerDto() {
        when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        CustomerDto savedCustomer = customerService.updateCustomer(1, customerDto);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getFirstName()).isEqualTo(customerDto.getFirstName());
        Assertions.assertThat(savedCustomer.getLastName()).isEqualTo(customerDto.getLastName());
        Assertions.assertThat(savedCustomer.getPhoneNumber()).isEqualTo(customerDto.getPhoneNumber());
        Assertions.assertThat(savedCustomer.getAppUser().getEmail()).isEqualTo(customerDto.getAppUser().getEmail());
    }

    @Test
    public void CustomerService_UpdateCustomerWithWrongId_ThrowResourceNotFoundException() {
        when(customerRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(1, customerDto));
    }

    @Test
    public void CustomerService_DeleteCustomerWithId_ThrowResourceNotFoundException() {
        when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(customer));

        customerService.deleteCustomer(1);
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).delete(customer);
    }


    @Test
    public void CustomerService_DeleteCustomerWithWrongId_ThrowResourceNotFoundException() {
        when(customerRepository.findById(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(1));
    }


}