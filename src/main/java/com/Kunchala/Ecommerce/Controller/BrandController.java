package com.Kunchala.Ecommerce.Controller;

import com.Kunchala.Ecommerce.Dto.BrandDto;
import com.Kunchala.Ecommerce.Service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<BrandDto> createBrand(@RequestBody @Validated BrandDto brandDto) {
        BrandDto createdBrand = brandService.createBrand(brandDto);
        return new ResponseEntity<>(createdBrand, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long id) {
        BrandDto brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable Long id, @RequestBody @Validated BrandDto brandDto) {
        BrandDto updatedBrand = brandService.updateBrand(id, brandDto);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
