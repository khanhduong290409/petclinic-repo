package com.petclinic.dtos;
import jakarta.validation.constraints.*;


public record PetDTO(
        Long id,
        @NotBlank @Size(max=120) String name,
        @Size(max=40) String species,// Loài: DOG, CAT,...
        @Size(max=80) String breed,// Giống: Husky, Poodle,...
        @Min(0) @Max(100) Integer age,
        @NotNull Long ownerId,
        String imageUrl

) {}
