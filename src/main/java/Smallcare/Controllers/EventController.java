package Smallcare.Controllers;

import Smallcare.Models.Event;
import Smallcare.Services.EventService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/events")
@Controller
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public String events(Model model){

        List <Event> events = eventService.findAll();
        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event,
                              @RequestParam(name = "startTime1") String startTime,
                              @RequestParam(name = "endTime1") String endTime, Model model){
//        event.setEndTime(endTime);
//        event.setStartTime(startTime);
        System.out.println(endTime + "\t" + startTime);
        Long id = eventService.save(event);
        System.out.println(id);
        return "index";
    }

    @GetMapping("/add")
    public String addPage(Model model){
        model.addAttribute("event", new Event());
        return "addEvent";
    }


}
