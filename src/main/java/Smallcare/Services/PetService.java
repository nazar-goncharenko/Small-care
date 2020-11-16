package Smallcare.Services;

import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import Smallcare.Models.User;
import Smallcare.Repositories.PetRepository;
import Smallcare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
