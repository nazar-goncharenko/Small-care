package Smallcare.Services;

import Smallcare.Models.*;
import Smallcare.Repositories.ConfirmedEventRepository;
import Smallcare.Repositories.EventCommentRepository;
import Smallcare.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.time.LocalDateTime.*;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserService userService;

    @Autowired
    ConfirmedEventRepository confirmedEventRepository;

    @Autowired
    EventCommentRepository commentRepository;

    @Autowired
    EventCommentRepository eventCommentRepository;


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

    public void deleteEvent(User user, Event event){
        User curUser = userService.findById(user.getId());
        curUser.deleteCreatedEvent(event);
        userService.save(user);
        event.clearPets();
        event.clearSinged();
        Set<EventComment> comments = new HashSet<>(event.getEventComments());
        event.clearComments();
        for (EventComment eventComment: comments) {
            eventComment.setUser(null);
            eventCommentRepository.delete(eventComment);
        }
        eventRepository.delete(event);
    }

    public void deleteConfirmedEvent(User user, ConfirmedEvent confirmedEvent){
        User curUser = userService.findById(user.getId());
        confirmedEvent.clearPets();
        confirmedEventRepository.delete(confirmedEvent);
    }

    public void confirmEvent(Event event, User user){
        confirmedEventRepository.save(new ConfirmedEvent(event, user));
        deleteEvent(event.getCreatorUser(), event);
    }

    public Set<ConfirmedEvent> getConfirmedEventForRate(User user){
        Set <ConfirmedEvent> confirmedEvents = confirmedEventRepository.getConfirmedEventByCreator(user);
        Set <ConfirmedEvent> output = new HashSet<>();
        for (ConfirmedEvent event : confirmedEvents) {
            if (event.getEndTime().isBefore(now())){
                System.out.println("DONE");
                event.setStatus(Status.DONE);
                confirmedEventRepository.save(event);
                output.add(event);
            }
        }
        return output;
    }

    public void addComment(Event event, EventComment eventComment){
        eventComment.setUser(userService.findById(eventComment.getUser().getId()));
        commentRepository.save(eventComment);
        event.addComment(eventComment);
        eventRepository.save(event);
    }

    public ConfirmedEvent getConfirmedEventById(Long id){
        return confirmedEventRepository.getById(id);
    }

    public void rateEvent(ConfirmedEvent confirmedEvent){
        confirmedEvent = confirmedEventRepository.getById(confirmedEvent.getId());
        confirmedEvent.setStatus(Status.RATED);
        confirmedEventRepository.save(confirmedEvent);
    }
}