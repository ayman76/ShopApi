package com.example.shopapi.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer productId;
    private String name;
    private String category;
    private String description;
    private String manufacture;
    private Double price;
    private Integer unitStock;
}
