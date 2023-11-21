package com.example.shopapi.controller;

import com.example.shopapi.dto.AppUserDto;
import com.example.shopapi.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.shopapi.utils.HelperFunctions.checkIfThereErrorsAndReturn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid AppUserDto appUserDto, BindingResult bindingResult) {
        ResponseEntity<StringBuilder> errorMessage = checkIfThereErrorsAndReturn(bindingResult);
        if (errorMessage != null) return errorMessage;
        return new ResponseEntity<>(appUserService.registerUser(appUserDto), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AppUserDto appUserDto, BindingResult bindingResult) {
        ResponseEntity<StringBuilder> errorMessage = checkIfThereErrorsAndReturn(bindingResult);
        if (errorMessage != null) return errorMessage;
        return new ResponseEntity<>(appUserService.loginUser(appUserDto), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody AppUserDto appUserDto, BindingResult bindingResult) throws Exception {
        ResponseEntity<StringBuilder> errorMessage = checkIfThereErrorsAndReturn(bindingResult);
        if (errorMessage != null) return errorMessage;
        return new ResponseEntity<>(appUserService.updateUserPassword(appUserDto), HttpStatus.OK);
    }
}
