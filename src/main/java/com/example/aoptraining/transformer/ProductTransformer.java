package com.example.aoptraining.transformer;

import com.example.aoptraining.dto.ProductDto;
import com.example.aoptraining.model.Product;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductTransformer {


    public Flux<ProductDto> toDto(Flux<Product> productFlux) {
        return productFlux.map(this::toDto);
    }

    public Mono<ProductDto> toDto(Mono<Product> productMono) {
        return productMono.map(this::toDto);
    }


    public ProductDto toDto(Product product)
    {
        return new ProductDto(
                        product.getId(),
                        product.getProductRef(),
                        product.getName(),
                        product.getPrice(),
                        product.getExpDate());
    }
    public Product toEntity(ProductDto productDto)
    {
        return new Product(productDto.id(),
                        productDto.productRef(),
                        productDto.name(),
                        productDto.price(),
                        productDto.expDate());

    }

    public List<Product> toEntities(List<ProductDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<ProductDto> toDtos(List<Product> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }


}
