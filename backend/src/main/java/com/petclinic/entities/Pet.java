package com.petclinic.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets") // Bảng trong DB tên "pets"
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính, auto increment
    private Long id;

    @Column(nullable=false, length=120) // Tên pet, bắt buộc
    private String name;

    @Column(nullable = false, length=40) // Loài: DOG, CAT,...
    private String species;

    @Column(length=80) // Giống: Husky, Poodle,...
    private String breed;

    private Integer age; // Tuổi

    // Nhiều Pet có thể thuộc 1 Owner
    // fetch=LAZY: chỉ load Owner khi gọi pet.getOwner()
    // @JoinColumn tạo khóa ngoại "owner_id" trỏ tới bảng owners
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(length=255)
    private String imageUrl; // http://localhost:8080/uploads/xxx.jpg

}
