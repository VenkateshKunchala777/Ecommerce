package com.Kunchala.Ecommerce.Repository;

import com.Kunchala.Ecommerce.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
