package com.example.shopapi.service;

import com.example.shopapi.dto.AppUserDto;
import com.example.shopapi.exception.BadRequestException;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.repository.AppUserRepository;
import com.example.shopapi.service.impl.AppUserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserServiceImpl appUserService;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        appUserService = new AppUserServiceImpl(appUserRepository, modelMapper);
    }

    @Test
    public void AppUserService_RegisterUser_ReturnAppUserDto() {
        AppUser appUser = AppUser.builder().id(1).email("user@example.com").password("12345678").build();
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("12345678").build();

        when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(appUser);

        AppUserDto savedUser = appUserService.registerUser(appUserDto);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void AppUserService_RegisterUserWithExistingEmail_ThrowsBadRequestException() {
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("12345678").build();
        when(appUserRepository.existsByEmail(Mockito.anyString())).thenThrow(BadRequestException.class);
        assertThrows(BadRequestException.class, () -> appUserService.registerUser(appUserDto));
    }

    @Test
    public void AppUserService_LoginUser_ReturnAppUserDto() {
        AppUser appUser = AppUser.builder().id(1).email("user@example.com").password("12345678").build();
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("12345678").build();

        when(appUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(appUser));

        AppUserDto loggedInUser = appUserService.loginUser(appUserDto);
        Assertions.assertThat(loggedInUser).isNotNull();
        Assertions.assertThat(loggedInUser.getEmail()).isEqualTo(appUserDto.getEmail());
        Assertions.assertThat(loggedInUser.getPassword()).isEqualTo(appUserDto.getPassword());
    }

    @Test
    public void AppUserService_LoginUserWithWrongPassword_ThrowsResourceNotFoundException() {
        AppUser appUser = AppUser.builder().id(1).email("user@example.com").password("12345678").build();
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("1234567").build();

        when(appUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(appUser));

        assertThrows(ResourceNotFoundException.class, () -> appUserService.loginUser(appUserDto));
    }

    @Test
    public void AppUserService_LoginWithNotFoundedEmail_ThrowsResourceNotFoundException() {
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("1234567").build();

        when(appUserRepository.findByEmail(Mockito.anyString())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> appUserService.loginUser(appUserDto));
    }


    @Test
    public void AppUserService_UpdateUser_ReturnAppUserDto() {
        AppUser appUser = AppUser.builder().id(1).email("user@example.com").password("123456789").build();
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("123456789").build();

        when(appUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(appUser));
        when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(appUser);

        AppUserDto updatedUser = appUserService.updateUserPassword(appUserDto);

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo(appUserDto.getPassword());
    }

    @Test
    public void AppUserService_UpdateUserWithNotFoundedEmail_ThrowsResourceNotFoundException() {
        AppUserDto appUserDto = AppUserDto.builder().email("user@example.com").password("1234567").build();

        when(appUserRepository.findByEmail(Mockito.anyString())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> appUserService.updateUserPassword(appUserDto));
    }


}