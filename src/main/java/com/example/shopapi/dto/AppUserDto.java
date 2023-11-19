package com.example.shopapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    @NotNull
    @Email(message = "Enter valid email format")
    private String email;
    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
