package com.petclinic.controllers;

import com.petclinic.dtos.PageRes;
import com.petclinic.dtos.ProductCreateReq;
import com.petclinic.dtos.ProductDetailRes;
import com.petclinic.dtos.ProductRes;
import com.petclinic.dtos.ProductUpdateReq;
import com.petclinic.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService svc;

    public AdminProductController(ProductService svc) {
        this.svc = svc;
    }

    // ========== LIST cho admin (có thể tái dụng list chung) ==========
    @GetMapping
    public PageRes<ProductRes> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "category") String categorySlug
    ) {
        return svc.list(keyword, categorySlug, page, size);
    }

    // ========== CREATE ==========
    @PostMapping
    public ProductDetailRes create(@RequestBody @Valid ProductCreateReq req) {
        return svc.create(req);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public ProductDetailRes update(@PathVariable Long id,
                                   @RequestBody @Valid ProductUpdateReq req) {
        return svc.update(id, req);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }

    // ========== UPLOAD ẢNH SẢN PHẨM ==========
    // gửi form-data: file=<MultipartFile>
    @PostMapping("/{id}/images")
    public ProductDetailRes uploadImage(@PathVariable Long id,
                                        @RequestPart("file") MultipartFile file) {
        return svc.addImage(id, file);
    }
}
