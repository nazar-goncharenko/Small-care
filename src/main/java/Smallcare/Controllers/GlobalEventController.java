package Smallcare.Controllers;

import Smallcare.Models.Event;
import Smallcare.Models.EventComment;
import Smallcare.Models.Status;
import Smallcare.Models.User;
import Smallcare.Services.EventService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/events")
@Controller
public class GlobalEventController {

    @Autowired
    EventService eventService;


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
        if (getCurrentUser() != null){
            model.addAttribute("creator_id", getCurrentUser().getId());
        }
        else {
            model.addAttribute("creator_id", null);
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

}
