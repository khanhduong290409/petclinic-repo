package com.petclinic.services;

import com.petclinic.dtos.*;
import com.petclinic.entities.Category;
import com.petclinic.entities.Product;
import com.petclinic.entities.ProductImage;
import com.petclinic.exceptions.NotFoundException;
import com.petclinic.repositories.CategoryRepository;
import com.petclinic.repositories.ProductRepository;
//import com.petclinic.services.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service // đánh dấu đây là spring bean
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    // ========== ADMIN CRUD ==========

    public ProductDetailRes create(ProductCreateReq req) {
        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + req.categoryId()));

        Product product = Product.builder()
                .name(req.name())
                .slug(req.slug())
                .shortDescription(req.shortDescription())
                .description(req.description())
                .price(req.price())
                .stock(req.stock())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return toDetailRes(saved);
    }

    public ProductDetailRes update(Long id, ProductUpdateReq req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        Category category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + req.categoryId()));

        product.setName(req.name());
        product.setSlug(req.slug());
        product.setShortDescription(req.shortDescription());
        product.setDescription(req.description());
        product.setPrice(req.price());
        product.setStock(req.stock());
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return toDetailRes(saved);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        productRepository.delete(product);
    }

    // ========== Upload ảnh sản phẩm ==========

    public ProductDetailRes addImage(Long productId, MultipartFile file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Lưu file vào thư mục uploads/ và nhận về fileName
        // Giả định: FileStorageService trả về fileName, còn static mapping: /uploads/{fileName}
        String fileName = fileStorageService.storeFile(file);
        String imageUrl = "/uploads/" + fileName; // đường dẫn public để FE dùng

        // Xác định position = size hiện tại
        int position = product.getImages().size();

        ProductImage img = ProductImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .position(position)
                .build();

        product.getImages().add(img);

        Product saved = productRepository.save(product);
        return toDetailRes(saved);
    }
    //API cho trang danh sach san pham
    public PageRes<ProductRes> list(String keyword, String categorySlug, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> p;
        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasCategoryslug = categorySlug != null && !categorySlug.isBlank();
        if(hasKeyword && hasCategoryslug) {
            p = productRepository.findByCategory_SlugAndNameContainingIgnoreCase(categorySlug, keyword, pageable);

        } else if (hasKeyword) {
            p = productRepository.findByNameContainingIgnoreCase(keyword, pageable);

        } else if (hasCategoryslug) {
            p = productRepository.findByCategory_Slug(categorySlug, pageable);

        } else {
            p = productRepository.findAll(pageable);
        }
        List<ProductRes> content = p.getContent()
                .stream()
                .map(this::toRes)
                .toList();
        return new PageRes<>(
                content,
                p.getNumber(), //số trang hiện tại (0-based).
                p.getSize(),
                p.getTotalElements(),
                p.getTotalPages()
        );

    }
    public ProductDetailRes getDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return toDetailRes(product);
    }
    public ProductDetailRes getDetailBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Product not found: " + slug));
        return toDetailRes(product);
    }
    private ProductRes toRes(Product p) {
        String categoryName = p.getCategory() != null ? p.getCategory().getName() : null;
        String thumnailUrl = p.getImages().isEmpty() ? null : p.getImages().get(0).getImageUrl();
        return new ProductRes(
                p.getId(),
                p.getName(),
                p.getSlug(),
                p.getPrice(),
                thumnailUrl,
                categoryName
        );
    }
    private ProductDetailRes toDetailRes(Product p) {
        String categoryName = p.getCategory() != null ? p.getCategory().getName() : null;

        List<String> imageUrls = p.getImages()
                .stream()
                .map(img -> img.getImageUrl())
                .toList();
        return new ProductDetailRes(
                p.getId(),
                p.getName(),
                p.getSlug(),
                p.getPrice(),
                p.getShortDescription(),
                p.getDescription(),
                categoryName,
                imageUrls
        );
    }


    }


