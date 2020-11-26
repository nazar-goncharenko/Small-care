package Smallcare.Services;

import Smallcare.IServices.IPetService;
import Smallcare.Models.Event;
import Smallcare.Models.Pet;
import Smallcare.Repositories.EventRepository;
import Smallcare.Repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService implements IPetService {
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

    public Long save(Pet pet) {
        petRepository.save(pet);
        return pet.getId();
    }

    public void deleteById(Long id){
        petRepository.deleteById(id);
    }
}
