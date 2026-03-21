package com.corporation_dev.product_microservice.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.corporation_dev.product_microservice.entity.Product;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{
}