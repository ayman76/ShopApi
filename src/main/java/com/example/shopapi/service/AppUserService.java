package com.example.shopapi.service;

import com.example.shopapi.dto.AppUserDto;

public interface AppUserService {
    AppUserDto registerUser(AppUserDto appUserDto);

    AppUserDto loginUser(AppUserDto appUserDto);

    AppUserDto updateUserPassword(AppUserDto appUserDto) throws Exception;
}
