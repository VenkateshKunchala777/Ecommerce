package com.Kunchala.Ecommerce.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDto {
    private Long id;
    @NotBlank(message = "Brand name is mandatory")
    private String name;
    private String description;
}
