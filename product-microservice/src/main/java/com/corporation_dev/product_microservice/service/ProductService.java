package com.corporation_dev.product_microservice.service;
import org.springframework.stereotype.Service;
import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.repository.ProductRepository;
import com.corporation_dev.product_microservice.util.EntityDtoUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CircuitBreaker(name = "getAllProductsCircuitBreaker", fallbackMethod = "getAllProductsFallback")
    public Flux<ProductDto> getAllProducts() {
        return this.productRepository.findAll()
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertUserFallback(Mono<ProductDto> userDto, Throwable ex) {
        System.out.println("Fallback insertUser - Error: " + ex.getMessage());
        return Mono.error(new RuntimeException("Servicio de usuarios no disponible temporalmente"));
    }
    
}
