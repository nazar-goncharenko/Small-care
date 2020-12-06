package Smallcare.Controllers.my;


import Smallcare.Models.Event;
import Smallcare.Models.Status;
import Smallcare.Models.User;
import Smallcare.Services.EventService;
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
import java.util.Set;

@Controller
@RequestMapping("/my/events")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

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
        Set < Event > crEvents = user.getCreatedEvents();
        if ( crEvents.contains(eventService.findById(id).get())){
            model.addAttribute("event", eventService.findById(id).get());
            return "event";
        }
        return "event";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event,
                              @RequestParam(name = "startTime1") String startTime,
                              @RequestParam(name = "endTime1") String endTime, Model model){
        LocalDateTime startTimeDataTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime endTimeDataTime = LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        event.setStartTime(startTimeDataTime);
        event.setEndTime(endTimeDataTime);
        event.setStatus(Status.REQUEST);
        User user = getCurrentUser();
        event.setCretorUser(user.getId());
        userService.addCreatedEvent(user, event);
        return "index";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        model.addAttribute("event", new Event());
        return "addEvent";
    }
}
