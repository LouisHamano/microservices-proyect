package com.corporation_dev.product_microservice.dto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {
    private String id;
    private String description;
    private int price; 
}
