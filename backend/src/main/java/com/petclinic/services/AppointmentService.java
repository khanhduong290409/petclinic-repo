package com.petclinic.services;

import com.petclinic.dtos.AppointmentDTO;
import com.petclinic.entities.Appointment;
import com.petclinic.entities.Pet;
import com.petclinic.exceptions.NotFoundException;
import com.petclinic.repositories.AppointmentRepository;
import com.petclinic.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppointmentService {
    private final AppointmentRepository repo;
    private final PetRepository petRepo;

    public AppointmentService(AppointmentRepository repo, PetRepository petRepo) {
        this.repo = repo; this.petRepo = petRepo;
    }

    public AppointmentDTO create(AppointmentDTO dto) {
        Pet pet = petRepo.findById(dto.petId())
                .orElseThrow(() -> new NotFoundException("Pet not found: " + dto.petId()));
        Appointment a = Appointment.builder()
                .pet(pet)
                .startTime(dto.startTime())
                .reason(dto.reason())
                .status(dto.status() == null ? "PENDING" : dto.status())
                .build();
        return toDTO(repo.save(a));
    }

    public List<AppointmentDTO> findAll() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    public List<AppointmentDTO> findByPet(Long petId) {
        return repo.findByPetId(petId).stream().map(this::toDTO).toList();
    }

    public AppointmentDTO update(Long id, AppointmentDTO dto) {
        Appointment a = repo.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found: " + id));
        if (dto.petId() != null && (a.getPet() == null || !dto.petId().equals(a.getPet().getId()))) {
            Pet pet = petRepo.findById(dto.petId())
                    .orElseThrow(() -> new NotFoundException("Pet not found: " + dto.petId()));
            a.setPet(pet);
        }
        if (dto.startTime() != null) a.setStartTime(dto.startTime());
        a.setReason(dto.reason());
        a.setStatus(dto.status());
        return toDTO(repo.save(a));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Appointment not found: " + id);
        repo.deleteById(id);
    }

    private AppointmentDTO toDTO(Appointment a) {
        Long petId = a.getPet() != null ? a.getPet().getId() : null;
        return new AppointmentDTO(a.getId(), petId, a.getStartTime(), a.getReason(), a.getStatus());
    }
}
