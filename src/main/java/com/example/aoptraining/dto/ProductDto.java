package com.example.aoptraining.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(String id, String productRef, String name, BigDecimal price, LocalDateTime expDate) {
}
