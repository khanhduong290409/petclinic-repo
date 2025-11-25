package com.petclinic.controllers;

import com.petclinic.dtos.OwnerDTO;
import jakarta.validation.Valid;
import com.petclinic.dtos.OwnerDTO;
import com.petclinic.services.OwnerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService svc;
    public OwnerController(OwnerService svc) { this.svc = svc; }

    @PostMapping("add") public OwnerDTO create(@Valid @RequestBody OwnerDTO dto) { return svc.create(dto); }
    @GetMapping("find-all") public List<OwnerDTO> all() { return svc.findAll(); }
    @GetMapping("find-by-id/{id}") public OwnerDTO one(@PathVariable Long id) { return svc.findById(id); }
    @PutMapping("update/{id}") public OwnerDTO update(@PathVariable Long id, @Valid @RequestBody OwnerDTO dto) { return svc.update(id, dto); }
    @DeleteMapping("delete/{id}") public void delete(@PathVariable Long id) { svc.delete(id); }
}
//@PathVariable dùng để bind tham số trong URL vào method parameter.
//@Valid (Jakarta Validation) báo cho Spring rằng đối tượng được truyền vào cần kiểm tra ràng buộc (validation).