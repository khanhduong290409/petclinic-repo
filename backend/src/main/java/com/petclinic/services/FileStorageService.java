package com.petclinic.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path uploadDir;

    //    public FileStorageService(@Value("${app.file.upload-dir}") String dir) throws IOException {
//        this.uploadDir = Paths.get(dir).toAbsolutePath().normalize();
//        Files.createDirectories(this.uploadDir);
//    }
    public FileStorageService(@Value("${app.file.upload-dir}") String dir) throws IOException {
        this.uploadDir = Paths.get(dir).toAbsolutePath().normalize(); // xem o trang 11 word
        Files.createDirectories(this.uploadDir);
    }

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename());
        //stringutils.cleanpath dung de xoa cac ki tu nguy hiem trong path
        //file.getoriginalFileName dung de lay ten va ca duoi file file user upload
        String ext = "";

        int dot = original.lastIndexOf(".");
        if (dot > 0) ext = original.substring(dot);//substring(dot) = cắt chuỗi từ vị trí dot đến hết.

        String newName = UUID.randomUUID() + "-" + Instant.now().toEpochMilli() + ext;
        //UUID = Universally Unique Identifier
        //→ Mã định danh ngẫu nhiên toàn cục.
        //ví dụ: 3f6c6c2a-4a4f-4c7e-9b9d-9df0a5b0a8e3
        //Instant.now() lấy thời gian hiện tại theo UTC. toEpochMilli() làm Jan 1, 1970 00:00:00 UTC
        //ví dụ: 1720433882000

        Path target = uploadDir.resolve(newName);// Ghép đường dẫn thư mục + tên file một cách an toàn và đúng chuẩn hệ điều hành.

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Cannot store file", e);
        }
        //StandardCopyOption.REPLACE_EXISTING
        //
        //Nếu file trùng tên → ghi đè.

//        return "/uploads/" + newName;
        return "/" + newName;


    }
}

//    public String saveImage(MultipartFile file) {
//        if(file == null || file.isEmpty()) {
//            return null;
//        }
//
//        // check file co null khong => neu co return null ( tuc la nguoi dung khong upload anh)
//
//        // lam sach duong dan file
//        // sau do tach duoi cua file ( neu khong co thi de rong) -> lay duoi file
//        // Tạo ra một UUID v4 ngẫu nhiên (128-bit), ví dụ:
//        // 3f6c6c2a-4a4f-4c7e-9b9d-9df0a5b0a8e3
//        //Xác suất trùng gần như bằng 0 ⇒ tên file hầu như không bị đụng nhau
//        //Khi nối chuỗi, Java tự gọi toString() trên UUID.
//        //Lấy thời điểm hiện tại theo UTC và đổi sang milliseconds từ 1970-01-01T00:00:00Z (Unix epoch).
//        // sau do cong them cai duoi file anh se ra ten file luu tren server
//        //muc dich cua viec nay:
////        tạo tên file duy nhất, an toàn, dễ truy vết.
////        Dấu - chỉ để phân tách cho dễ đọc, không bắt buộc nhưng rất nên dùng.
//        //Ghép tên file mới vào thư mục upload để có đường dẫn đầy đủ.
//        String original = StringUtils.cleanPath(file.getOriginalFilename());
//        String ext = "";
//        int dot = original.lastIndexOf(".");
//        if(dot > 0) ext = original.substring(dot);
//        String newName = UUID.randomUUID() + "-" + Instant.now().toEpochMilli()+ ext;
//        Path target = uploadDir.resolve(newName);
////        uploadDir là thư mục gốc để lưu ảnh (kiểu Path), ví dụ: /home/app/uploads (đã được tạo sẵn trong constructor).
////        resolve(newName) ghép “tên file” vào “thư mục gốc” theo chuẩn của hệ điều hành, tạo ra một Path mới trỏ tới file đích.
////        Ví dụ: uploadDir = /home/app/uploads, newName = "3f6c...-1726.jpg"
////        target = /home/app/uploads/3f6c...-1726.jpg
////        Ưu điểm dùng Path.resolve() (thay vì nối chuỗi): tránh lỗi dấu / hoặc \, đúng chuẩn cross-platform, an toàn hơn.
//
//        try(InputStream in = file.getInputStream()) {
//            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
//
//
//        } catch (IOException e) {
//            throw new RuntimeException("Cannot store file",  e);
//        }
//
//
//        return "/" + newName;
//
////        return "/" + newName;
//    }
//}
//Lấy InputStream từ MultipartFile để đọc tuần tự dữ liệu upload (không cần tải hết vào RAM).
//Dùng try-with-resources: Java sẽ tự đóng in (gọi in.close()) kể cả khi sao chép thành công hay phát sinh lỗi → tránh rò rỉ tài nguyên (memory/file descriptor).
//So với file.getBytes() (trả mảng byte): InputStream tốt hơn cho file lớn vì không phải giữ toàn bộ nội dung trong bộ nhớ cùng lúc.
//Files.copy(sourceStream, targetPath, option...) chép từ luồng vào đường dẫn đích.
//REPLACE_EXISTING: nếu file target đã tồn tại thì ghi đè.
//Ở code này tên file là UUID + thời gian, xác suất trùng tên cực thấp; tuỳ chọn này giúp an tâm nếu trùng (hiếm).