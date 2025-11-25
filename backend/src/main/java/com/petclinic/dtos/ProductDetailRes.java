package com.petclinic.dtos;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailRes (
        Long id,
        String name,
        String slug,
        BigDecimal price,
        String shortDescription,
        String description,
        String categoryName,
        List<String> imageUrls
){}
