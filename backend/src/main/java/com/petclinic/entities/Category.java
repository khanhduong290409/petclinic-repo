package com.petclinic.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="categories")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(length = 200)
    private String slug;
    // chuoi url de url than thien hon vd: /products/hat-cho-con-smartpup-1kg

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

}