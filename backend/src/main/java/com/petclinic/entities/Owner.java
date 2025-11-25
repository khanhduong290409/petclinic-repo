package com.petclinic.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity // Đánh dấu đây là entity JPA, ánh xạ với bảng DB
@Table(name = "owners") // Bảng trong DB tên "owners"
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
// Lombok sinh code getter/setter, constructor, builder
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Khóa chính, auto increment (Postgres serial/identity)
    private Long id;

    @Column(nullable=false, length=120)
    // Bắt buộc, tối đa 120 ký tự
    private String name;

    @Column(nullable=false, length=20)
    private String phone;

    @Column(nullable=false, length=160)
    private String email;


    // Quan hệ 1 Owner có nhiều Pet
    // mappedBy="owner" nghĩa là mapping ngược lại ở field "owner" trong Pet
    // cascade=ALL: khi xóa/sửa Owner thì các Pet cũng bị ảnh hưởng
    // orphanRemoval=true: nếu bỏ Pet ra khỏi list thì Pet đó cũng bị xóa trong DB
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    @CreationTimestamp
    // Tự động set khi tạo record
    private Instant createdAt;

    @UpdateTimestamp
    // Tự động update khi record thay đổi
    private Instant updatedAt;
}
