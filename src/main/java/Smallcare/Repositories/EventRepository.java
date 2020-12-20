package Smallcare.Repositories;

import Smallcare.Models.Event;
import Smallcare.Models.Pet;
import Smallcare.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findAll();
    void deleteEventById(Long id);
    List<Event> getAllByPetsContains(Pet pet);
}
