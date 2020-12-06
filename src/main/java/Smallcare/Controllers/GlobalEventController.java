package Smallcare.Controllers;

import Smallcare.Models.Event;
import Smallcare.Models.Status;
import Smallcare.Services.EventService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping
    public String events(Model model){

        List <Event> events = eventService.findAll();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/{id}")
    public String getEventById(Model model,@PathVariable Long id){
        model.addAttribute("event" , eventService.findById(id));
        return "event";
    }


}
