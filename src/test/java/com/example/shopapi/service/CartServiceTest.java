package com.example.shopapi.service;

import com.example.shopapi.dto.CartDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.model.Cart;
import com.example.shopapi.model.Customer;
import com.example.shopapi.repository.CartRepository;
import com.example.shopapi.service.impl.CartServiceImpl;
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
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        cartService = new CartServiceImpl(cartRepository, modelMapper);
        Customer customer = Customer.builder().customerId(1).firstName("Ayman").lastName("Mohamed").phoneNumber("0123456789").appUser(new AppUser()).build();
        cart = Cart.builder().cartId(1).totalPrice(10.0).customer(customer).build();
    }


    @Test
    public void CartService_GetCartByCustomerId_ReturnCartDto() {
        when(cartRepository.findByCustomer_CustomerId(Mockito.anyInt())).thenReturn(Optional.ofNullable(cart));

        CartDto foundedCart = cartService.getCartByCustomerId(1);

        assertThat(foundedCart).isNotNull();
        assertThat(foundedCart.getCartId()).isEqualTo(1);
    }

    @Test
    public void CartService_GetCartByNotFoundedCustomerId_ThrowsResourceNotFoundedException() {
        when(cartRepository.findByCustomer_CustomerId(Mockito.anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> cartService.getCartByCustomerId(1));
    }


}