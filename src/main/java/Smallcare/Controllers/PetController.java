package Smallcare.Controllers;


import Smallcare.IServices.IPetService;
import Smallcare.Models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.io.*;
import java.util.List;


@RequestMapping("/user/pets")
@Controller
public class PetController {

    @Autowired
    IPetService petService;

    @Value("${upload.path}")
    private String upload_path;

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
        model.addAttribute("pet", new Pet());
        return "addPet";
    }

    @PostMapping("/add")
    public String postPet(Model model,
                          @ModelAttribute Pet pet,
                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        model.addAttribute("pet", new Pet());
        if(pet.getName()!=null){
            Long pet_id = petService.save(pet);
            if(file != null){
                file.transferTo(new File(upload_path + pet_id.toString() + ".png"));
            }
        }
        return "redirect:";
    }
}