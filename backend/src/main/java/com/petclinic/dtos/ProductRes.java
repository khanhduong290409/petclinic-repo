package com.petclinic.dtos;

import java.math.BigDecimal;

public record ProductRes  ( // dùng cho trang danh sách sản phẩm
        Long id,
        String name,
        String slug, // Slug chỉ dùng để tìm sản phẩm qua URL
        BigDecimal price, // giá
        String thumbnailUrl, // thumbnailUrl chính là ảnh đại diện (first image) lấy từ product.images.
        String categoryName //
) {}
//Record tự sinh :

//✔ constructor
//✔ getter (id(), name(), slug()…)
//✔ equals()
//✔ hashCode()
//✔ toString()
// dùng như DTO trả về API