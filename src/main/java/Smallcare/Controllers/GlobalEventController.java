package Smallcare.Controllers;

import Smallcare.Models.Event;
import Smallcare.Models.EventComment;
import Smallcare.Models.Status;
import Smallcare.Models.User;
import Smallcare.Services.EventService;
import Smallcare.Services.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import groovyjarjarpicocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequestMapping("/events")
@Controller
public class GlobalEventController {

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
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("owner", false);
        if (getCurrentUser() != null){
            model.addAttribute("user", getCurrentUser());
        }
        return "events";
    }

    @GetMapping("/{id}")
    public String getEventById(Model model, @PathVariable Long id) {
        User user = getCurrentUser();
        if (eventService.findById(id).isPresent()) {
            Event event = eventService.findById(id).get();
            model.addAttribute("event", event);
            if (user != null) {
                model.addAttribute("user", getCurrentUser());
                if (user.getId().equals(event.getCreatorUser().getId())) {
                    model.addAttribute("owner", true);
                } else {
                    model.addAttribute("owner", false);
                }
            }
            return "event";
        }
        return "event";
    }

    @PostMapping("/{id}/comment")
    public String addCommentToEvent(@PathVariable Long id, @RequestParam(name = "comment") String comment, Model model) {
        Optional<Event> event = eventService.findById(id);
        if (event.isPresent()) {
            eventService.addComment(event.get(), new EventComment(getCurrentUser(), comment));
            return getEventById(model, id);
        }
        return "redirect:/events/" + id;
    }

    @PostMapping("/{event_id}/confirm/{user_id}")
    public String confirmEvent(@PathVariable Long event_id,@PathVariable Long user_id, Model model){
        Event event = eventService.findById(event_id).get();
        User user = userService.findById(user_id);
        if (event == null || user == null){
            return events(model);
        }
        eventService.confirmEvent(event, user);
        return "redirect:/";
    }

}
