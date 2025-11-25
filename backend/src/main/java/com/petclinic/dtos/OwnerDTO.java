package com.petclinic.dtos;

import jakarta.validation.constraints.*;


public record OwnerDTO (
    Long id,
    @NotBlank @Size(max=120) String name,
    @Size(max=20) String phone,
    @Email @Size(max=160) String email

) {}
