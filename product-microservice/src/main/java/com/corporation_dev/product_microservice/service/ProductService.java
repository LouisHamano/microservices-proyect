package com.corporation_dev.product_microservice.service;
import org.springframework.stereotype.Service;
import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.repository.ProductRepository;
import com.corporation_dev.product_microservice.util.EntityDtoUtil;
import reactor.core.publisher.Flux;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> getAllProducts() {
        return this.productRepository.findAll()
            .map(EntityDtoUtil::toDto);
    }
    
}
