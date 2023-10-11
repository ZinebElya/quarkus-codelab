package com.example;

import com.example.src.Pet;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PetMapper {
    public PetDTO toDTO(Pet pet) {
        return new PetDTO(pet.getId(), pet.getName(), pet.getKind(), pet.getProfileText());
    }

    public List<PetDTO> toDTO(List<Pet> pets) {
        return pets.stream().map(this::toDTO).collect(Collectors.toList());

    }
}
