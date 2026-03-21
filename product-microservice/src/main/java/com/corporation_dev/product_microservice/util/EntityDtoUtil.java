package com.corporation_dev.product_microservice.util;

import org.springframework.beans.BeanUtils;

import com.corporation_dev.product_microservice.dto.ProductDto;
import com.corporation_dev.product_microservice.entity.Product;

public class EntityDtoUtil {
    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);

        return productDto;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        
        return product;
    }
}
