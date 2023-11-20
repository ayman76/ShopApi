package com.example.shopapi.service.impl;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.ShippingAddressDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.Customer;
import com.example.shopapi.model.ShippingAddress;
import com.example.shopapi.repository.CustomerRepository;
import com.example.shopapi.repository.ShippingAddressRepository;
import com.example.shopapi.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.shopapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {
    private final ShippingAddressRepository shippingAddressRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public ShippingAddressDto createShippingAddress(ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = modelMapper.map(shippingAddressDto, ShippingAddress.class);
        Customer customer = customerRepository.findById(shippingAddress.getCustomer().getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Not founded customer with id: " + shippingAddress.getCustomer().getCustomerId()));
        shippingAddress.setCustomer(customer);
        ShippingAddress savedShippingAddress = shippingAddressRepository.save(shippingAddress);

        return modelMapper.map(savedShippingAddress, ShippingAddressDto.class);
    }

    @Override
    public ApiResponse<ShippingAddressDto> getAllShippingAddress(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll(pageable);
        List<ShippingAddress> listOfShippingAddress = shippingAddresses.getContent();
        List<ShippingAddressDto> content = listOfShippingAddress.stream().map(s -> modelMapper.map(s, ShippingAddressDto.class)).toList();

        return getApiResponse(pageNo, pageSize, content, shippingAddresses);
    }

    @Override
    public ShippingAddressDto getShippingAddressById(int id) {
        ShippingAddress foundedShippingAddress = shippingAddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Shipping Address with id: " + id));
        return modelMapper.map(foundedShippingAddress, ShippingAddressDto.class);
    }

    @Override
    public ShippingAddressDto updateShippingAddress(int id, ShippingAddressDto shippingAddressDto) {
        ShippingAddress foundedShippingAddress = shippingAddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Shipping Address with id: " + id));
        foundedShippingAddress.setAddress(shippingAddressDto.getAddress());
        foundedShippingAddress.setCity(shippingAddressDto.getCity());
        foundedShippingAddress.setCountry(shippingAddressDto.getCountry());
        foundedShippingAddress.setState(shippingAddressDto.getState());
        foundedShippingAddress.setZipcode(shippingAddressDto.getZipcode());

        ShippingAddress updatedShippingAddress = shippingAddressRepository.save(foundedShippingAddress);

        return modelMapper.map(updatedShippingAddress, ShippingAddressDto.class);
    }

    @Override
    public void deleteShippingAddress(int id) {
        ShippingAddress foundedShippingAddress = shippingAddressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Shipping Address with id: " + id));
        shippingAddressRepository.delete(foundedShippingAddress);
    }
}
