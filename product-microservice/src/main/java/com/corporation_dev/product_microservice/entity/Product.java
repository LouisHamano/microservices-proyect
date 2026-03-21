package com.corporation_dev.product_microservice.entity;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
    @Id 
    private String id;
    private String description;
    private int price; 
}
