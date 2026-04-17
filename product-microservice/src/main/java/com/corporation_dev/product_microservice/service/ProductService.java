package com.corporation_dev.product_microservice.service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.repository.ProductRepository;
import com.corporation_dev.product_microservice.util.EntityDtoUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private WebClient webClient;

    public ProductService(ProductRepository productRepository, WebClient webClient) {
        this.productRepository = productRepository;
        this.webClient = webClient;
    }

    /* Obtener todos los productos */
    @CircuitBreaker(name = "getAllProductsCircuitBreaker", fallbackMethod = "getAllProductsFallback")
    public Flux<ProductDto> getAllProducts() {
        return this.productRepository.findAll()
            .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getAllProductsFallback(Throwable ex) {
        System.out.println("Fallback - Error: " + ex.getMessage());
        return Flux.error(new RuntimeException("Servicio de usuarios no disponible temporalmente"));
    }

    /* Obtener producto por id */
    @CircuitBreaker(name = "getProductByIdCircuitBreaker", fallbackMethod = "getProductByIdFallback")
    public Mono<ProductDto> getProductById(int id) {
        return this.productRepository.findById(id)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductByIdFallback(Throwable ex) {
        return Mono.error(new RuntimeException("Servicio de usuarios no disponible temporalmente"));
    }

    /* Insertar producto */
    @CircuitBreaker(name = "insertProductCircuitBreaker", fallbackMethod = "insertProductFallback")
    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDto) {
        return productDto.map(EntityDtoUtil::toEntity)
            .flatMap(productRepository::save)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProductFallback(Throwable ex) {
        return Mono.error(new RuntimeException("Servicio de usuarios no disponible temporalmente"));
    }

    /* Actualizar producto */
    @CircuitBreaker(name = "updateProductByIdCircuitBreaker", fallbackMethod = "updateProductByIdFallback")
    public Mono<ProductDto> updateProductById(Mono<ProductDto> productDto, int id) {
        return productDto
            .map(EntityDtoUtil::toEntity)
            .doOnNext(product -> product.setId(id))
            .flatMap(productRepository::save)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProductByIdFallback(Throwable ex) {
        System.out.println("Fallback - Error: " + ex.getMessage());
        return Mono.error(new RuntimeException("Servicio de usuarios no disponible temporalmente"));
    }

    /* Probar el circuit breaker */
    @CircuitBreaker(name = "testCircuitBreaker", fallbackMethod = "testCircuitBreakerFallback")
    public Mono<String> testCircuitBreaker() {
        return webClient
            .get()
            .uri("http://www.googleerror.com")
            .retrieve()
            .bodyToMono(String.class)
            .map(res -> "Operación exitosa")
            .doOnError(ex -> System.out.println("-> Error capturado: " + ex.getClass().getSimpleName()))
            .onErrorMap(ex -> new RuntimeException("Servicio de prueba no disponible"));
    }

    public Mono<String> testCircuitBreakerFallback(Throwable ex) {
        System.out.println("-> FALLBACK ejecutado: Servicio temporalmente no disponible");
        return Mono.just("Fallback: Servicio no disponible");
    }
}
