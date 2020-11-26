package Smallcare.Services;


import Smallcare.Models.Pet;
import Smallcare.Repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    final
    PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll(){
        return petRepository.findAll();
    }

    public Optional<Pet> findById(Long id){
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public void deleteById(Long id){
        petRepository.deleteById(id);
    }
}
