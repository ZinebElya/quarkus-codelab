package com.example.repositories;

import com.example.src.Kind;
import com.example.src.Pet;
import com.example.PetMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class PetRepository {

    List<Pet> pets = new ArrayList<>();
    Pet pet = new Pet("1", "pet1", Kind.CATS, "texte111");



    public List<Pet> getAllPets(){
        pets.add(pet);

        return pets;
    }

}
