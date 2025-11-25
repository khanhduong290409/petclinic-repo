package com.petclinic.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateReq(
        @NotBlank
        @Size(max = 160)
        String name,

        @NotBlank
        @Size(max = 200)
        String slug,

        @Size(max = 255)
        String shortDescription,

        String description,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stock,

        @NotNull
        Long categoryId
) {}
