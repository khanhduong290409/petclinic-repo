package com.petclinic.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;// dung de lay id product
    @Column(nullable = false, length = 255)
    private String imageUrl;// link anh
    private Integer position;//so thu tu cua anh

}
/*
* @ManyToOne có nghĩa là:

Trong class hiện tại (ProductImage), có một field tham chiếu đến Product,
và quan hệ là Many-to-One (nhiều ảnh thuộc một sản phẩm).

🟢 FetchType.LAZY

fetch = LAZY nghĩa là:

👉 Không load Product ngay lập tức khi truy vấn ProductImage.
Chỉ khi nào bạn gọi productImage.getProduct() thì Hibernate mới truy vấn DB để lấy sản phẩm.
*
*
*
* 🟦 @JoinColumn(name = "product_id", nullable = false)
1️⃣ @JoinColumn là gì?

Nó nói với Hibernate rằng:

Cột khóa ngoại trong bảng product_images là cột product_id.

Hibernate sẽ tạo bảng như sau:

CREATE TABLE product_images (
  id BIGINT PRIMARY KEY,
  image_url VARCHAR(255),
  position INT,
  product_id BIGINT NOT NULL  -- khóa ngoại
);

2️⃣ nullable = false

Nghĩa là:

ProductImage bắt buộc phải có product

Một ảnh không thể tồn tại nếu không thuộc sản phẩm nào
*
* */
