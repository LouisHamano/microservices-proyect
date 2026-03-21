package com.corporation_dev.product_microservice.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
@Tag(name = "Product API", description = "Operaciones CRUD del producto")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("all")
    public Flux<ProductDto> all() {
        return this.productService.getAll();
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id) {
        return this.productService.getProductById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Insertar productos a la DB")
    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDto) {
        return this.productService.insertProduct(productDto);
    }

    @Operation(summary = "Actualizar productos por ID")
    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDto) {
        return this.productService.updateProduct(id, productDto)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar producto por ID")
    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return this.productService.deleteProduct(id);
    }
}
