package com.Kunchala.Ecommerce.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phone;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Address> addressList;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Order> orderList;
}
