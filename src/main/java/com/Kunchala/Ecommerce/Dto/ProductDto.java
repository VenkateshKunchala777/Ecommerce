package com.Kunchala.Ecommerce.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    @NotBlank(message = "Product name is mandatory")
    private String name;
    private String description;
    @Positive(message = "Price must be positive")
    private Double price;
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer stock;
}
