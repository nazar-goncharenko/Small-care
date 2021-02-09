package Smallcare.Services;


import Smallcare.Models.ConfirmedEvent;
import Smallcare.Models.Event;
import Smallcare.Models.Pet;
import Smallcare.Models.User;
import Smallcare.Repositories.ConfirmedEventRepository;
import Smallcare.Repositories.EventRepository;
import Smallcare.Repositories.PetRepository;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ConfirmedEventRepository confirmedEventRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }


    public void deletePet(User user, Pet pet) {
        for (Event event: eventRepository.getAllByPetsContains(pet)) {
            eventService.deleteEvent(user, event);
        }
        for (ConfirmedEvent confirmedEvent : confirmedEventRepository.getAllByPetsContains(pet)){
            eventService.deleteConfirmedEvent(user, confirmedEvent);
        }
        user.deletePet(pet);
        userService.save(user);
        petRepository.delete(pet);
    }
}
