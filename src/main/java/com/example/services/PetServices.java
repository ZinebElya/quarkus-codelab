package com.example.services;

import com.example.PetDTO;
import com.example.PetMapper;
import com.example.repositories.PetRepository;
import com.example.src.Pet;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PetServices {
    private PetRepository petRepository;
    private PetMapper petMapper;

    public PetServices(PetRepository petRepository, PetMapper petMapper){
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    public List<PetDTO> getAllPets(){
        return petMapper.toDTO(petRepository.getAllPets());

    }




}
