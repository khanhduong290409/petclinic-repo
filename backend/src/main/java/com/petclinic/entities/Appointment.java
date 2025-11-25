package com.petclinic.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments") // Bảng trong DB tên "appointments"
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính, auto increment
    private Long id;

    // Nhiều Appointment có thể thuộc về 1 Pet
    // fetch=LAZY: chỉ load Pet khi gọi appointment.getPet()
    // @JoinColumn tạo khóa ngoại "pet_id" trỏ tới bảng pets
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private LocalDateTime startTime; // Thời gian hẹn khám

    @Column(length=255) // Lý do khám, tối đa 255 ký tự
    private String reason;

    @Column(length=20) // Trạng thái: PENDING, CONFIRMED, DONE, CANCELED
    private String status;
}
