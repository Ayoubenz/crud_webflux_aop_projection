package com.example.aoptraining.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("products")
public class Product {

    @Id
    private String id;
    private String productRef;
    private String name;

    private BigDecimal price;

    private LocalDateTime expDate;


}
