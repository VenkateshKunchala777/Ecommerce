package com.Kunchala.Ecommerce.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    @NotNull(message = "Order date is mandatory")
    private LocalDate orderDate;
    @NotBlank(message = "Status is mandatory")
    private String status;
    private Double totalAmount;
    private List<OrderItemDto> orderItems;
}
