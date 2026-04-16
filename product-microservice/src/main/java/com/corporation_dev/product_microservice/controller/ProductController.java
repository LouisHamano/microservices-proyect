package com.corporation_dev.product_microservice.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.corporation_dev.product_microservice.service.ProductService;
import reactor.core.publisher.Mono;

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
}
