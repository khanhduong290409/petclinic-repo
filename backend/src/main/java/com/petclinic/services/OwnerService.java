package com.petclinic.services;

import com.petclinic.dtos.OwnerDTO;
import com.petclinic.entities.Owner;
import com.petclinic.repositories.OwnerRepository;
import com.petclinic.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OwnerService {
    private final OwnerRepository repo;
    public OwnerService(OwnerRepository repo) { this.repo = repo; }
    public OwnerDTO create(OwnerDTO owner) {
        Owner o = Owner.builder()
                .name(owner.name())
                .phone(owner.phone())
                .email(owner.email())
                .build();
        o = repo.save(o);
        return toDTO(o);
    }
    public List<OwnerDTO> findAll() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }
    public OwnerDTO findById(Long id) {
        Owner o = repo.findById(id).orElseThrow(() -> new NotFoundException("Owner not found: " + id));
        return toDTO(o);
    }
    public OwnerDTO update(Long id, OwnerDTO dto) {
        Owner o = repo.findById(id).orElseThrow(() -> new NotFoundException("Owner not found"));
        o.setName(dto.name());
        o.setPhone(dto.phone());
        o.setEmail(dto.email());
        return toDTO(repo.save(o));
    }
    public void delete(Long id) {
        if(!repo.existsById(id))  throw new NotFoundException("Owner not found");{
            repo.deleteById(id);
        }
    }

    private OwnerDTO toDTO(Owner o) {
        return new OwnerDTO(o.getId(), o.getName(), o.getPhone(), o.getEmail());
    }

}
