package com.Kunchala.Ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String pincode;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    @JsonIgnore
    private Customer customer;
}
