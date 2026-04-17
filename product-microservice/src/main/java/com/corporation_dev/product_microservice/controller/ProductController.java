package com.corporation_dev.product_microservice.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.service.ProductService;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<Map<String, Object>>> getAllProducts() {
        return this.productService.getAllProducts()
            .collectList()
            .map(products -> {
                Map<String, Object> res = new HashMap<>();
                res.put("status", true);
                res.put("products", products);
                return ResponseEntity.ok(res);
            })
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> getProductById(@PathVariable int id) {
        return this.productService.getProductById(id)
            .map(product -> {
                Map<String, Object> res = new HashMap<>();
                res.put("status", true);
                res.put("product", product);
                return ResponseEntity.ok(res);
            })
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/insert")
    public Mono<ResponseEntity<Map<String, Object>>> insertProduct(@RequestBody Mono<ProductDto> productDto) {
        return this.productService.insertProduct(productDto)
            .map(product -> {
                Map<String, Object> res = new HashMap<>();
                res.put("status", true);
                res.put("product", product);
                return ResponseEntity.ok(res);
            })
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateProductById(@RequestBody Mono<ProductDto> productDto, @PathVariable int id) {
        return this.productService.updateProductById(productDto, id)
            .map(product -> {
                Map<String, Object> res = new HashMap<>();
                res.put("status", true);
                res.put("product", product);
                return ResponseEntity.ok(res);
            })
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<String>> testCircuit() {
        return productService.testCircuitBreaker()
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(503).build());
    }
    
}
