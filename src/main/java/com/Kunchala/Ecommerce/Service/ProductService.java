package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.ProductDto;
import com.Kunchala.Ecommerce.Entity.Brand;
import com.Kunchala.Ecommerce.Entity.Category;
import com.Kunchala.Ecommerce.Entity.Product;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.BrandRepository;
import com.Kunchala.Ecommerce.Repository.CategoryRepository;
import com.Kunchala.Ecommerce.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper,CategoryRepository categoryRepository,BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository=categoryRepository;
        this.brandRepository=brandRepository;
    }

    public ProductDto createProduct(ProductDto productDto,Long category_id,Long brand_id) {
        Product product = modelMapper.map(productDto, Product.class);
        boolean isCategoryExisted=categoryRepository.existsById(category_id);
        if(!isCategoryExisted) throw new ResourceNotFoundException("Category with the id "+ category_id+" is not found to attach to the product");
        Category category=categoryRepository.findById(category_id).get();
        product.setCategory(category);
        boolean isBrandExists=brandRepository.existsById(brand_id);
        if(!isBrandExists) throw new ResourceNotFoundException("Brand with the id " + brand_id +" is not found to attach to the product");
        Brand brand=brandRepository.findById(brand_id).get();
        product.setBrand(brand);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return modelMapper.map(product, ProductDto.class);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        productDto.setId(id);
        modelMapper.map(productDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    public ProductDto patchProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (productDto.getName() != null) existingProduct.setName(productDto.getName());
        if (productDto.getDescription() != null) existingProduct.setDescription(productDto.getDescription());
        if (productDto.getPrice() != null) existingProduct.setPrice(productDto.getPrice());
        if (productDto.getStock() != null) existingProduct.setStock(productDto.getStock());

        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
