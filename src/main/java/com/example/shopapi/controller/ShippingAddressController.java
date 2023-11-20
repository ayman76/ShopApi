package com.example.shopapi.controller;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.ShippingAddressDto;
import com.example.shopapi.service.ShippingAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.shopapi.utils.HelperFunctions.checkIfThereErrorsAndReturn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shippingAddress")
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;

    @PostMapping("/create")
    public ResponseEntity<?> createShippingAddress(@RequestBody @Valid ShippingAddressDto shippingAddressDto, BindingResult bindingResult) {
        ResponseEntity<StringBuilder> errorMessage = checkIfThereErrorsAndReturn(bindingResult);
        if (errorMessage != null) return errorMessage;
        return new ResponseEntity<>(shippingAddressService.createShippingAddress(shippingAddressDto), HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> getAllShippingAddress(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(shippingAddressService.getAllShippingAddress(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddressDto> getShippingAddressById(@PathVariable int id) {
        return new ResponseEntity<>(shippingAddressService.getShippingAddressById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShippingAddress(@PathVariable int id, @RequestBody @Valid ShippingAddressDto shippingAddressDto, BindingResult bindingResult) {
        ResponseEntity<StringBuilder> errorMessage = checkIfThereErrorsAndReturn(bindingResult);
        if (errorMessage != null) return errorMessage;

        return new ResponseEntity<>(shippingAddressService.updateShippingAddress(id, shippingAddressDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable int id) {
        shippingAddressService.deleteShippingAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
