package com.example.shopapi.service;

import com.example.shopapi.dto.ApiResponse;
import com.example.shopapi.dto.ProductDto;
import com.example.shopapi.model.Product;
import com.example.shopapi.repository.ProductRepository;
import com.example.shopapi.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final int PRODUCT_ID = 1;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        productService = new ProductServiceImpl(productRepository, modelMapper);
        productDto = ProductDto.builder().name("product name").category("product category").description("product description").manufacture("product manufacture").price(5.99).unitStock(100).build();
        product = Product.builder().productId(1).name("product name").category("product category").description("product description").manufacture("product manufacture").price(5.99).unitStock(100).build();
    }

    @Test
    public void ProductService_CreateProduct_ReturnProductDto() {

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ProductDto savedProduct = productService.createProduct(productDto);

        Assertions.assertThat(savedProduct).isNotNull();
    }

    @Test
    public void ProductService_CreateManyProducts_ReturnProductDto() {

        List<Product> products = Arrays.asList(product, product, product);
        List<ProductDto> productDtos = Arrays.asList(productDto, productDto, productDto);
        when(productRepository.saveAll(Mockito.anyList())).thenReturn(products);

        List<ProductDto> savedProducts = productService.createManyProducts(productDtos);

        Assertions.assertThat(savedProducts).isNotEmpty();
        Assertions.assertThat(savedProducts.size()).isEqualTo(products.size());
    }

    @Test
    public void ProductService_GetAllProducts_ReturnProductDtos() {

        Page<Product> products = Mockito.mock(Page.class);

        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(products);

        ApiResponse<ProductDto> foundedProducts = productService.getAllProducts(0, 10);

        Assertions.assertThat(foundedProducts).isNotNull();
        Assertions.assertThat(foundedProducts.getContent()).isNotNull();
    }

    @Test
    public void ProductService_GetProductById_ReturnProductDto() {

        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));

        ProductDto foundedProduct = productService.getProductById(PRODUCT_ID);

        Assertions.assertThat(foundedProduct).isNotNull();
        Assertions.assertThat(foundedProduct.getProductId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    public void ProductService_UpdateProduct_ReturnProductDto() {

        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ProductDto updatedProduct = productService.updateProduct(PRODUCT_ID, productDto);

        Assertions.assertThat(updatedProduct).isNotNull();
        Assertions.assertThat(updatedProduct.getProductId()).isEqualTo(PRODUCT_ID);
        Assertions.assertThat(updatedProduct.getName()).isEqualTo(productDto.getName());
        Assertions.assertThat(updatedProduct.getDescription()).isEqualTo(productDto.getDescription());
        Assertions.assertThat(updatedProduct.getManufacture()).isEqualTo(productDto.getManufacture());
        Assertions.assertThat(updatedProduct.getCategory()).isEqualTo(productDto.getCategory());
        Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(productDto.getPrice());
        Assertions.assertThat(updatedProduct.getUnitStock()).isEqualTo(productDto.getUnitStock());
    }

    @Test
    public void ProductService_DeleteProduct_ReturnVoid() {

        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(product));

        assertAll(() -> productService.deleteProductById(PRODUCT_ID));
    }


}