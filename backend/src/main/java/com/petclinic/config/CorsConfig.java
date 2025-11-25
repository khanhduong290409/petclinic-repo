package com.petclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//đánh dấu đây là class cấu hình spring
@Configuration
public class CorsConfig {
    @Bean // spring quản lí method này như 1 bean
    public CorsFilter corsFilter() {
        // Tạo object chứa cấu hình CORS = Cross-Origin Resource Sharing (Chia sẻ tài nguyên khác nguồn).
        //một cơ chế bảo mật của browser (trình duyệt).
        CorsConfiguration cfg = new CorsConfiguration();
        // Chỉ cho phép frontend React (chạy ở http://localhost:5173) gọi API
        cfg.setAllowedOrigins(List.of("http://localhost:5173"));
        // Cho phép các method HTTP này (CRUD + OPTIONS cho preflight request)
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        // Cho phép tất cả header (Content-Type, Authorization, …)
        cfg.setAllowedHeaders(List.of("*"));
        // Có cho phép gửi cookie/JWT kèm request không
        // false = không cho phép, true = cho phép
        cfg.setAllowCredentials(false);
        // Áp dụng cấu hình CORS cho tất cả endpoint có prefix /api/**
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", cfg);
        // Trả về filter để Spring Boot áp dụng
        return new CorsFilter(source);
    }
}
//        CorsConfiguration
//        Đây là class cấu hình do Spring cung cấp.
//        Nhiệm vụ: định nghĩa chính sách CORS (ai được phép gọi API, gọi bằng cách nào).


//        Đây là một Spring Filter (lọc request trước khi đến Controller).
//        Khi request đi vào server, CorsFilter sẽ:
//        Kiểm tra request có đến từ origin hợp lệ không.
//        Kiểm tra request dùng method có được phép không.
//        Kiểm tra headers có hợp lệ không.
//        Nếu hợp lệ → cho request tiếp tục vào controller.
//        Nếu không hợp lệ → trả về lỗi CORS error.
//        CorsFilter cần được tạo với một CorsConfigurationSource (nguồn cung cấp rule CORS).