package Smallcare.Repositories;

import Smallcare.Models.Pet;
import Smallcare.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
    List<Pet> findAll();
}
