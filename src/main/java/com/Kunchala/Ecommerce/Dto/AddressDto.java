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
public class AddressDto {
    private Long id;
    @NotBlank(message = "House number is mandatory")
    private String houseNo;
    @NotBlank(message = "Street is mandatory")
    private String street;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "State is mandatory")
    private String state;
    @NotBlank(message = "Pincode is mandatory")
    private String pincode;
}
