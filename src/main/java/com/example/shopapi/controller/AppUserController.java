package com.example.shopapi.controller;

import com.example.shopapi.dto.AppUserDto;
import com.example.shopapi.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<AppUserDto> registerUser(@RequestBody AppUserDto appUserDto) {
        return new ResponseEntity<>(appUserService.registerUser(appUserDto), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<AppUserDto> loginUser(@RequestBody AppUserDto appUserDto) {
        return new ResponseEntity<>(appUserService.loginUser(appUserDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AppUserDto> updateUser(@RequestBody AppUserDto appUserDto) throws Exception {
        return new ResponseEntity<>(appUserService.updateUserPassword(appUserDto), HttpStatus.OK);
    }
}
