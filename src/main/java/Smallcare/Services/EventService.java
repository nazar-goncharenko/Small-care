package Smallcare.Services;

import Smallcare.Models.Event;
import Smallcare.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<Event> findAll(){
        return eventRepository.findAll();
    }


    public Event findById(Long id){
        return eventRepository.findById(id).get();
    }

    public Long save(Event event) {
        eventRepository.save(event);
        return event.getId();
    }
}