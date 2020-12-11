package Smallcare.Repositories;

import Smallcare.Models.Pet;
import Smallcare.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
    List<Pet> findAll();
    Optional<Pet> findById(Long id);
}
