package com.example.aoptraining.controller;

import com.example.aoptraining.dto.ProductDto;
import com.example.aoptraining.dto.ProductDtoProjection;
import com.example.aoptraining.model.Product;
import com.example.aoptraining.projectionTest.ProductProjection;
import com.example.aoptraining.service.Impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/id/{id}")
    public Mono<ResponseEntity<ProductDto>> findProductById(@PathVariable String id)
    {
        return productService.findProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/byRef/{ref}")
    public Mono<ResponseEntity<ProductDto>> findByProductRef(@PathVariable String ref) {
        return productService.findByProductRef(ref)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }
    @PostMapping("/save-batch")
    public Flux<ProductDto> saveProducts(@RequestBody List<ProductDto> productDtoList) {
        Flux<ProductDto> productDtoFlux = Flux.fromIterable(productDtoList);
        return productDtoFlux
                .buffer(500) // buffer 100 products before processing
                .flatMap(productService::saveProducts);
    }




    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteProductById(@PathVariable String id) {
        return productService.deleteProductById(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/all")
    public Flux<ProductDto> findAllProducts()
    {
       return productService.findAllProducts();
    }

    @GetMapping("/allProjected")
    public Flux<ProductDtoProjection> findAllProductsProjected(
            @RequestParam(defaultValue = "2") int page,
            @RequestParam(defaultValue = "100") int size)
    {
        return productService.findAllProductsProjected(page,size);
    }

    @GetMapping("/allProjectedQuery2")
    public Flux<ProductDtoProjection> findAllProductsProjectedQuery2()
    {
        return productService.findAllProductsProjectedQuery2();
    }


    @GetMapping("/allProjected2")
    public Flux<ProductProjection> findAllProductsProjected2() {
        return productService.findAllProductsProjected2();
    }
}
