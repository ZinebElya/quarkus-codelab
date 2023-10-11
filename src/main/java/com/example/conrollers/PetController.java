package com.example.conrollers;

import com.example.PetDTO;
import com.example.services.PetServices;
import com.example.src.Pet;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/pets")
public class PetController {
    private PetServices petServices;

    public PetController(PetServices petServices) {
        this.petServices = petServices;
    }

    @GET
    @Path("/hello")
    public String helloPets(){
        return "Hello Pets!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PetDTO> getAllPets(){
        return petServices.getAllPets();

    }

}
