package com.example.aoptraining.service.Impl;

import com.example.aoptraining.Exception.ProductException;
import com.example.aoptraining.aspect.CheckNotNullFields;
import com.example.aoptraining.dto.ProductDto;
import com.example.aoptraining.dto.ProductDtoProjection;
import com.example.aoptraining.model.Product;
import com.example.aoptraining.projectionTest.ProductProjection;
import com.example.aoptraining.repository.ProductRepository;
import com.example.aoptraining.transformer.ProductTransformer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {

    private final ProductRepository productRepository;
    private final ProductTransformer productTransformer;

    public ProductServiceImpl(ProductRepository productRepository, ProductTransformer productTransformer) {
        this.productRepository = productRepository;
        this.productTransformer = productTransformer;
    }

    public Mono<ProductDto> findProductById(String id)
    {
       return productTransformer.toDto(productRepository.findById(id));
    }

    public Mono<ProductDto> findByProductRef(String ref)
    {
       return productTransformer.toDto(productRepository.findByProductRef(ref));
    }

    @CheckNotNullFields
    public Mono<ProductDto> saveProduct(ProductDto productDto)
    {
        Product product = productTransformer.toEntity(productDto);
         Mono<Product> productMono= productRepository.save(product);
         return productTransformer.toDto(productMono);
    }

    public Flux<ProductDto> saveProducts(List<ProductDto> productDtos) {
        List<Product> products = productDtos.stream()
                .map(productTransformer::toEntity)
                .collect(Collectors.toList());

       return   productRepository.insert(products).map(productTransformer::toDto);
    }



    public Mono<ProductDto> updateProduct(String id,ProductDto productDto)
    {
        return productRepository.findById(id).flatMap(existingProduct ->
                {
                existingProduct.setId(productDto.id());
                existingProduct.setProductRef(productDto.productRef());
                existingProduct.setName(productDto.name());
                existingProduct.setPrice(productDto.price());
                existingProduct.setExpDate(productDto.expDate());
                return productTransformer.toDto(productRepository.save(existingProduct));
                }).switchIfEmpty(Mono.error(new ProductException("Product not found with ID: " + id)));
    }

    public Mono<Void> deleteProductById(String id)
    {
        return productRepository.findById(id)
                .flatMap(existingProduct -> productRepository.delete(existingProduct).then())
                .switchIfEmpty(Mono.error(new ProductException("Product not found with ID: " + id)));
    }

    public Flux<ProductDto> findAllProducts()
    {
        return productRepository.findAll()
                .map(productTransformer::toDto);
    }
    public Flux<ProductDtoProjection> findAllProductsProjected(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findAllProductsProjected(pageable);
    }

    public Flux<ProductDtoProjection> findAllProductsProjectedQuery2() {

        return productRepository.findAllProductsProjected2();
    }

    public Flux<ProductProjection> findAllProductsProjected2() {
        return productRepository.findAllProjectedBy();
    }


}
