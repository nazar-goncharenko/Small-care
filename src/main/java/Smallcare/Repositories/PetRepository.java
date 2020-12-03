package Smallcare.Repositories;

import Smallcare.Models.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
    List<Pet> findAll();

    Optional<Pet> findById(Long id);
}
