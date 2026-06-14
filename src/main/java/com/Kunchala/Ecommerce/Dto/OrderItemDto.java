package com.Kunchala.Ecommerce.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    private Double priceAtPurchase;
    @NotNull(message = "Product ID is mandatory")
    private Long productId;
    private String productName;
}
