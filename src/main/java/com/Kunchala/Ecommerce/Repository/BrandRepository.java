package com.Kunchala.Ecommerce.Repository;

import com.Kunchala.Ecommerce.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
