package Smallcare.Controllers.my;


import Smallcare.Models.*;
import Smallcare.Services.EventService;
import Smallcare.Services.PetService;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/my/events")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return ((User) auth.getPrincipal());
    }

    @GetMapping
    public String events(Model model){
        if (getCurrentUser().getCreatedEvents() != null){
            model.addAttribute("events", getCurrentUser().getCreatedEvents());
            return "events";
        }
        return "events";
    }

    @GetMapping("/{id}")
    public String getEventById(Model model,@PathVariable Long id){
        User user = getCurrentUser();
        if (eventService.findById(id).isPresent()){
            Event event = eventService.findById(id).get();
            model.addAttribute("event", event);
            if(user != null){
                if(user.getId().equals(event.getCreatorUser().getId())){
                    model.addAttribute("owner", true);
                } else {
                    model.addAttribute("owner", false);
                }
            }
            return "event";
        }
        return "event";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event,
                              @RequestParam(name = "startTime1") String startTime,
                              @RequestParam(name = "endTime1") String endTime, 
                              @RequestParam(value = "fieldIdList[]") Long[] fieldIdList){
        LocalDateTime startTimeDataTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime endTimeDataTime = LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        event.setStartTime(startTimeDataTime);
        event.setEndTime(endTimeDataTime);
        event.setStatus(Status.REQUEST);
//      ======= Massive of Pets id =======
        for (Long i:fieldIdList) {
            if ( petService.findById(i).isPresent()) {
                event.addPet(petService.findById(i).get());
            }
        }
//      =======                    =======
        User curUser = getCurrentUser();
        userService.addCreatedEvent(curUser, event);
        return "events";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        model.addAttribute("event", new Event());
        User user = getCurrentUser();
        Set<Pet> petList = userService
                .findById(
                        (user).getId())
                .getPetList();
        if (petList != null) {
            model.addAttribute("pets", petList);
        }
        return "addEvent";
    }

    @PostMapping("/{id}/comment")
    public String addCommentToEvent(@PathVariable Long id, @RequestParam(name = "comment") String comment){
        Optional<Event> event = eventService.findById(id);
        if(event.isPresent()){
            event.get().addComment(new EventComment(getCurrentUser(), comment));
            eventService.save(event.get());
            return "redirect:/events/" + id;
        }
        return "redirect:/events/" + id;
    }

    @GetMapping("/signed")
    public String getSignedEvents(Model model){
        if ( !getCurrentUser().getSignedEvents().isEmpty() ) {
            model.addAttribute("events", getCurrentUser().getSignedEvents());
        }
        return "events";
    }

    @PostMapping("{id}/sign")
    public String addSignedEvent(@PathVariable Long id, Model model){
        Optional<Event> optionalEvent = eventService.findById(id);
        if(optionalEvent.isEmpty()){
            model.addAttribute("error", true);
            return "redirect:/events";
        }
        Event event = optionalEvent.get();
        if (!event.getCreatorUser().getId().equals(Objects.requireNonNull(getCurrentUser()).getId())) {
            event.addSingedUser(getCurrentUser());
            //eventService.save(event);
            getCurrentUser().addSignedEvent(event);
        }
        else {
            model.addAttribute("error", true);
            return "redirect:/events";
        }
        return "redirect:/events/" + id;
    }
}
