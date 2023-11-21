package com.example.shopapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Integer customerId;
    @NotNull(message = "First Name must be not null")
    private String firstName;
    @NotNull(message = "Last Name must be not null")
    private String lastName;
    @NotNull(message = "Phone Number must be not null")
    private String phoneNumber;
    private AppUserDto appUser;
    private ShippingAddressDto shippingAddress;
    private BillingAddressDto billingAddress;
}
