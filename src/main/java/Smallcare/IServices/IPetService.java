package Smallcare.IServices;

import Smallcare.Models.Pet;

import java.util.List;
import java.util.Optional;

public interface IPetService {
    List<Pet> findAll();
    Optional<Pet> findById(Long id);
    Long save(Pet pet);
    void deleteById(Long id);
}
