package Smallcare.IServices;
import Smallcare.Models.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> findAll();
    Optional<Event> findById(Long id);
    Long save(Event event);
}
