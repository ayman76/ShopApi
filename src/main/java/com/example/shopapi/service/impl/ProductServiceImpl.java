package com.example.shopapi.service.impl;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.ProductDto;
import com.example.shopapi.exception.ResourceNotFoundException;
import com.example.shopapi.model.Product;
import com.example.shopapi.repository.ProductRepository;
import com.example.shopapi.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> createManyProducts(List<ProductDto> productDtos) {
        List<Product> products = productDtos.stream().map(p -> modelMapper.map(p, Product.class)).toList();
        List<Product> savedProducts = productRepository.saveAll(products);
        return savedProducts.stream().map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }

    @Override
    public ApiResponse<ProductDto> getAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream().map(p -> modelMapper.map(p, ProductDto.class)).toList();

        return getApiResponse(pageNo, pageSize, content, products);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product foundedProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Product with id: " + id));
        return modelMapper.map(foundedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto productDto) {
        Product foundedProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Product with id: " + id));
        foundedProduct.setName(productDto.getName());
        foundedProduct.setDescription(productDto.getDescription());
        foundedProduct.setCategory(productDto.getCategory());
        foundedProduct.setManufacture(productDto.getManufacture());
        foundedProduct.setPrice(productDto.getPrice());
        foundedProduct.setUnitStock(productDto.getUnitStock());
        Product updatedProduct = productRepository.save(foundedProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProductById(Integer id) {
        Product foundedProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Founded Product with id: " + id));
        productRepository.delete(foundedProduct);
    }
}
