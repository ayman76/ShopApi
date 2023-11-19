package com.example.shopapi.service.impl;

import com.example.shopapi.dto.AppUserDto;
import com.example.shopapi.exception.BadRequestException;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.AppUser;
import com.example.shopapi.repository.AppUserRepository;
import com.example.shopapi.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;


    @Override
    public AppUserDto registerUser(AppUserDto appUserDto) {
        if (!isValidEmail(appUserDto.getEmail())) {
            throw new BadRequestException("Email is not valid");
        }
        if (appUserRepository.existsByEmail(appUserDto.getEmail())) {
            throw new BadRequestException("Email is already taken");
        }
        if (isValidPassword(appUserDto.getPassword())) {
            throw new BadRequestException("Password length should be greater than 8 and less than 50.");
        }
        AppUser appUser = modelMapper.map(appUserDto, AppUser.class);
        appUser.setEnabled(true);
        AppUser registeredUser = appUserRepository.save(appUser);

        return modelMapper.map(registeredUser, AppUserDto.class);
    }

    @Override
    public AppUserDto loginUser(AppUserDto appUserDto) {
        AppUser foundedUser = appUserRepository.findByEmail(appUserDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Not Founded user with this email:" + appUserDto.getEmail()));
        if (!foundedUser.getPassword().equals(appUserDto.getPassword())) {
            throw new ResourceNotFoundException("Not Correct email or password");
        }
        return modelMapper.map(foundedUser, AppUserDto.class);
    }

    @Override
    public AppUserDto updateUserPassword(AppUserDto appUserDto) {
        AppUser foundedUser = appUserRepository.findByEmail(appUserDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Not Founded user with this email:" + appUserDto.getEmail()));
        if (isValidPassword(appUserDto.getPassword())) {
            throw new BadRequestException("Password length should be greater than 8 and less than 50.");
        }
        foundedUser.setPassword(appUserDto.getPassword());

        AppUser updatedUser = appUserRepository.save(foundedUser);
        return modelMapper.map(updatedUser, AppUserDto.class);
    }

    private boolean isValidPassword(String password) {
        return password == null || password.length() < 8 || password.length() > 50;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
