package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.BrandDto;
import com.Kunchala.Ecommerce.Entity.Brand;
import com.Kunchala.Ecommerce.Entity.Product;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandService(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    public BrandDto createBrand(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
       List<Product> productList=brand.getProductList();
       if(productList!=null) {
           for(Product product: productList) {
               product.setBrand(brand);
           }
       }
        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandDto.class);
    }

    public BrandDto getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return modelMapper.map(brand, BrandDto.class);
    }

    public List<BrandDto> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brand -> modelMapper.map(brand, BrandDto.class))
                .collect(Collectors.toList());
    }

    public BrandDto updateBrand(Long id, BrandDto brandDto) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));

        brandDto.setId(id);
        modelMapper.map(brandDto, existingBrand);
        Brand updatedBrand = brandRepository.save(existingBrand);
        return modelMapper.map(updatedBrand, BrandDto.class);
    }

    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}
