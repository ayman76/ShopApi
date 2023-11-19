package com.example.shopapi.service;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> createManyProducts(List<ProductDto> productDtos);

    ApiResponse<ProductDto> getAllProducts(int pageNo, int pageSize);

    ProductDto getProductById(Integer id);

    ProductDto updateProduct(Integer id, ProductDto productDto);

    void deleteProductById(Integer id);


}
