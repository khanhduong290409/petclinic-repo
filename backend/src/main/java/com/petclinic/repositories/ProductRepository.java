package com.petclinic.repositories;

import com.petclinic.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategory_Slug(String slug, Pageable pageable);
    Page<Product> findByCategory_SlugAndNameContainingIgnoreCase(
            String slug,
            String keyword,
            Pageable pageable
    );

    Optional<Product> findBySlug(String slug);


}
