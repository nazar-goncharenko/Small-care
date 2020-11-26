package Smallcare.Services;

import Smallcare.Models.Event;
import Smallcare.IServices.IEventService;
import Smallcare.Repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EventService implements IEventService {
    final
    EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id){
        return eventRepository.findById(id);
    }

    public Long save(Event event) {
        eventRepository.save(event);
        return event.getId();
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}