package Smallcare.Services;

import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import Smallcare.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService implements IPetService {
    @Autowired
    PetRepository petRepository;

    public List<Pet> getAll(){
        return petRepository.findAll();
    }
}
