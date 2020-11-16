package Smallcare.Controllers;


import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.io.IOException;
import java.util.List;

@RequestMapping("/user/pets")
@Controller
public class PetController {

    @Autowired
    IPetService petService;

    @GetMapping("")
    public String pets(Model model) {
        List<Pet> pets = petService.findAll();
        if (!pets.isEmpty()) {
            model.addAttribute("pets", pets);
        }
        return "pets";
    }

    @GetMapping("/{id}")
    public String pet(Model model, @PathVariable Long id) {
        Pet pet = petService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (pet != null) {
            model.addAttribute("pet", pet);
        }
        return "pet";
    }

    @GetMapping("/add")
    public String addPet(Model model) {
        return "add-pet";
    }

    @PostMapping("/add")
    public String postPet(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam(required = false) MultipartFile file,
                          Model model) {
//        trackService.addTrack(file, name);
        petService.save(new Pet());
        return "pets";
    }

}









