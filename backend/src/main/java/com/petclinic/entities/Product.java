package com.petclinic.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;// kieu so dung cho gia tien, chinh xac tuyet doi khong nhu double
import java.util.*;

@Entity @Table(name="products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(nullable = false, unique = true, length = 200)
    //unique nó đảm bảo trong db không có hai dòng(record) nào
    //có giá trị giôgns nhau ở cột  đó
    private String slug;
    // slug dùng cho URL thân thiện: /products/hat-cho-con-smartpup-1kg


    @Column(length = 255)
    private String shortDescription; // mô tả ngắn hiển thị ở card sản phẩm

    @Column(columnDefinition = "TEXT")
    //columnDefinition = "TEXT" chỉ định loại dữ liệu trong PostgreSQL là TEXT, không phải VARCHAR.
    //TEXT cho phép lưu nội dung dài, không giới hạn 255 ký tự.
    private String description;      // mô tả chi tiết hiển thị ở trang chi tiết

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @Column(nullable = false, precision = 12, scale = 2)
    //② precision = 12
    //Tổng số chữ số (digits) có thể lưu = 12.  -> 1234567890.99   (12 digits)
    //③ scale = 2
    //Có 2 chữ số thập phân (phần sau dấu phẩy).
    private BigDecimal price; // giá bán

    @Column(precision = 12, scale = 2)
    private BigDecimal discountPrice; // null nếu không giảm

    @Column(nullable = false)
    private Integer stock; // tồn kho

//    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private ProductDetail detail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    //mappedBy = "product" nghĩa là trường product nằm trong ProductImage mới là bên giữ khoá ngoại
    // nghĩa là nó sẽ nói trường ProductImage là bên giữ khoá ngoại product_id nên ở Product không cần phải tạo khoá ngoại
    //tìm hiểu cascade = CascadeType.All là gì trong word
    //✔ orphanRemoval = true
    //
    //Nếu một ProductImage BỊ XÓA khỏi list:
    //
    //product.getImages().remove(img);
    //
    //
    //→ Hibernate sẽ tự động DELETE ảnh đó khỏi DB.
    //
    //Nghĩa là ảnh không thuộc sản phẩm nào nữa → xóa "con mồ côi".
    @OrderBy("position ASC") //List ảnh sẽ luôn sắp xếp theo position tăng dần.
    // Khi load product.getImages(), thứ tự luôn chính xác.
    @Builder.Default // 👈 THÊM DÒNG NÀY // dòng này quan trong vì nếu không có sẽ bị lôi
    //xem chi tiết ở word trang 9
    private List<ProductImage> images = new ArrayList<>();

    @Column(updatable = false)
    //Nghĩa là:
    //
    //📌 Khi record được update, cột createdAt không được phép thay đổi.
    //
    //Tức:
    //
    //Khi insert → có giá trị
    //
    //Khi update → giữ nguyên
    //
    //→ “Ngày tạo” chỉ ghi 1 lần duy nhất.
    @Temporal(TemporalType.TIMESTAMP)
    //JPA cần biết kiểu thời gian để map chính xác vào SQL.
    //
    //TemporalType.TIMESTAMP = lưu cả ngày + giờ:
    //ví dụ: 2025-02-19 14:33:55
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    //xem PrePersist và PreUpdate trong word trang
    void prePersist() {
        Date now = new Date();
        createdAt = now;
        updatedAt = now;
    }
    @PreUpdate
    void preUpdate() {
        updatedAt = new Date();
    }
}