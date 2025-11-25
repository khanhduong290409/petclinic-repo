package com.petclinic.services;

import com.petclinic.dtos.OwnerDTO;
import com.petclinic.dtos.PetDTO;
import com.petclinic.entities.Owner;
import com.petclinic.entities.Pet;
import com.petclinic.exceptions.NotFoundException;
import com.petclinic.repositories.OwnerRepository;
import com.petclinic.repositories.PetRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class  PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public PetDTO create(PetDTO dto) {
        Owner owner = ownerRepository.findById(dto.ownerId())
                .orElseThrow(() -> new NotFoundException("Owner not found: " + dto.ownerId()));
        Pet p = Pet.builder()
                .name(dto.name())
                .species(dto.species())
                .breed(dto.breed())
                .age(dto.age())
                .owner(owner)
                .imageUrl(dto.imageUrl())
                .build();
        return toDTO(petRepository.save(p));
    }

    public List<PetDTO> findAll() {
        return petRepository.findAll().stream().map(this::toDTO).toList();
    }
    public List<PetDTO> findByOwner(Long ownerId) {
        return petRepository.findById(ownerId).stream().map(this::toDTO).toList();

    }
    public PetDTO findById(Long id) {
        Pet p = petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found: " + id));
        return toDTO(p);
    }
    public PetDTO update(Long id, PetDTO dto) {
        Pet p = petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found: " + id));
        p.setName(dto.name());
        p.setSpecies(dto.species());
        p.setBreed(dto.breed());
        p.setAge(dto.age());
        p.setImageUrl(dto.imageUrl());
        if(dto.ownerId() != null && (p.getOwner() == null || !dto.ownerId().equals(p.getOwner().getId()))) {
            Owner owner = ownerRepository.findById(dto.ownerId())
                    .orElseThrow(() -> new NotFoundException("Owner not found: " + dto.ownerId()));
            p.setOwner(owner);
        }
        return toDTO(petRepository.save(p));
    }
    public void delete(Long id) {
        if (!petRepository.existsById(id)) throw new NotFoundException("Pet not found: " + id);
        petRepository.deleteById(id);
    }

    private PetDTO toDTO(Pet p) {
        Long ownerId = p.getOwner() != null ? p.getOwner().getId() : null;
        return new PetDTO(p.getId(), p.getName(), p.getSpecies(), p.getBreed(), p.getAge(), ownerId, p.getImageUrl());
    }
}
