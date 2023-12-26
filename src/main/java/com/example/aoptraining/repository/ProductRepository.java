package com.example.aoptraining.repository;

import com.example.aoptraining.dto.ProductDto;
import com.example.aoptraining.dto.ProductDtoProjection;
import com.example.aoptraining.model.Product;
import com.example.aoptraining.projectionTest.ProductProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,String> {
    Mono<Product> findByProductRef(String productRef);

    @Query(value = "{}", fields = "{ 'id' : 1, 'price' : 1 }")
    Flux<ProductDtoProjection> findAllProductsProjected(Pageable pageable);

    @Query(value = "{}", fields = "{ 'id' : 1, 'price' : 1 }")

    Flux<ProductDtoProjection> findAllProductsProjected2();


    Flux<ProductProjection> findAllProjectedBy();
}
