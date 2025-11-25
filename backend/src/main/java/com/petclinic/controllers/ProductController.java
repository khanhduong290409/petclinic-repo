package com.petclinic.controllers;


import com.petclinic.dtos.PageRes;
import com.petclinic.dtos.ProductDetailRes;
import com.petclinic.dtos.ProductRes;
import com.petclinic.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService svc;
    public ProductController(ProductService svc) {
        this.svc = svc;
    }
    @GetMapping
    public PageRes<ProductRes> list (
       @RequestParam(defaultValue = "0") int page,
       @RequestParam(defaultValue = "12") int size,
       @RequestParam(required = false) String keyword,
       @RequestParam(required = false, name = "category") String categorySlug

    ) {
        return svc.list(keyword, categorySlug, page, size);
    }
    @GetMapping("/{id}")
    public ProductDetailRes detail(@PathVariable Long id) {
        return svc.getDetail(id);
    }
    @GetMapping("/slug/{slug}")
    public ProductDetailRes detailBySlug(@PathVariable String slug) {
        return svc.getDetailBySlug(slug);
    }

}
