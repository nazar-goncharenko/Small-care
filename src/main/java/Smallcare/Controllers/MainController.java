package Smallcare.Controllers;

import Smallcare.Models.Event;
import Smallcare.Models.Pet;
import Smallcare.Models.User;
import Smallcare.Controllers.my.EventController;
import Smallcare.Services.EventService;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

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

    @GetMapping("/")
    public ModelAndView index(Model model){
        User user = getCurrentUser();
        if (user == null) {
            model.addAttribute("user", null);
            return new ModelAndView("index");
        }
        else {
            model.addAttribute("rateEvents", eventService.getConfirmedEventForRate(getCurrentUser()));
        }
        model.addAttribute("user", user);
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        Iterable<Pet> pets = userService
                .findById(
                        (user).getId())
                .getPetList();
        model.addAttribute("pets", pets);
        return new ModelAndView("index");
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "signUp";
    }
}