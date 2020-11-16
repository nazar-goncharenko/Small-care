package Smallcare.Controllers;


import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PetController {

    @Autowired
    IPetService petService;

    @GetMapping("/pets")
    @ResponseBody
    public String pets (Model model){
        List<Pet> pets = petService.getAll();
        if (!pets.isEmpty()) {
            model.addAttribute("pets", pets);
        }
        return "Allpets";
    }
}
