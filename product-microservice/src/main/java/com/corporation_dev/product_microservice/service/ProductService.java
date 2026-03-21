package com.corporation_dev.product_microservice.service;
import org.springframework.stereotype.Service;
import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.repository.ProductRepository;
import com.corporation_dev.product_microservice.util.EntityDtoUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> getAll() {
        return this.productRepository.findAll()
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return this.productRepository.findById(id)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDto) {
        return productDto.map(EntityDtoUtil::toEntity)
            .flatMap(this.productRepository::insert)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDto) {
        return this.productRepository.findById(id)
            .flatMap(product -> productDto.map(EntityDtoUtil::toEntity))
            .doOnNext(e -> e.setId(id))
            .flatMap(this.productRepository::save)
            .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.productRepository.deleteById(id);
    }

}
