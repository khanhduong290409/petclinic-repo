package com.petclinic.controllers;
import com.petclinic.dtos.AppointmentDTO;
import com.petclinic.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService svc;
    public AppointmentController(AppointmentService svc) { this.svc = svc; }

    @PostMapping("add-appo") public AppointmentDTO create(@Valid @RequestBody AppointmentDTO dto) { return svc.create(dto); }
    @GetMapping("find-all-appo") public List<AppointmentDTO> all() { return svc.findAll(); }
    @GetMapping("find-appo-by-pet/{petId}") public List<AppointmentDTO> byPet(@PathVariable Long petId) { return svc.findByPet(petId); }
    @PutMapping("update-appo/{id}") public AppointmentDTO update(@PathVariable Long id, @Valid @RequestBody AppointmentDTO dto) { return svc.update(id, dto); }
    @DeleteMapping("delete-appo/{id}") public void delete(@PathVariable Long id) { svc.delete(id); }
}