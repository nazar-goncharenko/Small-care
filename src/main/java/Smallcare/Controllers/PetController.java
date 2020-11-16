package Smallcare.Controllers;


import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@RequestMapping("/pets")
@Controller
public class PetController {

    @Autowired
    IPetService petService;

    @GetMapping("")
    @ResponseBody
    public String pets(Model model) {
        List<Pet> pets = petService.findAll();
        if (!pets.isEmpty()) {
            model.addAttribute("pets", pets);
        }
        return "Allpets";
    }

    @GetMapping("/{id}")
    public String pet(Model model, @PathVariable Long id) {
//        Optional<Pet> optionalPet = petService.findById(id);
//        Pet pet = optionalPet.isPresent() ? optionalPet.get() : new Pet();
        Pet pet = petService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (pet != null) {
            model.addAttribute("pets", pet);
        }
        return "pet";
    }
}
