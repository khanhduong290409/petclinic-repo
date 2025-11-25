package com.petclinic.controllers;
import com.petclinic.dtos.PetDTO;
import com.petclinic.services.FileStorageService;
import com.petclinic.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService svc;
    private final FileStorageService fileStorageService;
    public PetController(PetService svc, FileStorageService fileStorageService) {
        this.svc = svc;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/add") public PetDTO create(@Valid @RequestBody PetDTO dto) { return svc.create(dto); }
    @GetMapping("/find-all")public List<PetDTO> all() { return svc.findAll(); }
    @GetMapping("/find-by-id/{id}") public PetDTO one(@PathVariable Long id) { return svc.findById(id); }
    @GetMapping("/find-by-owner/{ownerId}") public List<PetDTO> byOwner(@PathVariable Long ownerId) { return svc.findByOwner(ownerId); }
    @PutMapping("/update/{id}") public PetDTO update(@PathVariable Long id, @Valid @RequestBody PetDTO dto) { return svc.update(id, dto); }
    @DeleteMapping("/delete/{id}") public void delete(@PathVariable Long id) { svc.delete(id); }
    @PostMapping( path ="/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PetDTO createWithImage(
            @Valid @RequestPart("data") PetDTO dto,                      // <-- tên "data" KHỚP với FormData.append("data", ...)
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        PetDTO created = svc.create(dto); // tạo pet trước
        String url = fileStorageService.storeFile(image); // lưu file, trả về "/uploads/xxx.jpg"
        if (url != null) {
            PetDTO patch = new PetDTO(
                    created.id(), created.name(), created.species(), created.breed(),
                    created.age(), created.ownerId(), url
            );
            return svc.update(created.id(), patch); // cập nhật imageUrl
        }
        return created;
    }

    @PutMapping(path = "/update-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PetDTO updateImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image                  // <-- tên "image" KHỚP với FormData.append("image", ...)
    ) {
        String url = fileStorageService.storeFile(image);
        PetDTO current = svc.findById(id);
        PetDTO patch = new PetDTO(
                current.id(), current.name(), current.species(), current.breed(),
                current.age(), current.ownerId(), url
        );
        return svc.update(id, patch);
    }


}