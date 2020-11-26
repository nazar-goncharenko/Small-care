package Smallcare.Controllers;


import Smallcare.Models.Pet;
import Smallcare.Models.User;
import Smallcare.Services.PetService;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    PetService petService;

    @Autowired
    UserService userService;

    @Value("${upload.path}")
    private String upload_path;

    @GetMapping("")
    public String pets(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "index";
        }
        Iterable<Pet> petList = userService
                .findById(
                        ((User) auth
                                .getPrincipal())
                                .getId())
                .getPetList();
        System.out.println(petList != null);
        if (petList != null) {
            model.addAttribute("pets", petList);
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "index";
        }
        if (pet.getName() != null) {
            User user = (User) auth.getPrincipal();
            Pet newpet = petService.save(pet);
            userService.addPet(user, pet);
            if (file != null) {
                file.transferTo(new File(upload_path + newpet.getId().toString() + ".png"));
            }
        }

        Iterable<Pet> petList = userService
                .findById(
                        ((User) auth
                                .getPrincipal())
                                .getId())
                .getPetList();
        System.out.println(petList != null);
        if (petList != null) {
            model.addAttribute("pets", petList);
        }
        return "pets";
    }

    @GetMapping("/delete/{id}")
    public String deletePet(Model model, @PathVariable Long id) {
        petService.deleteById(id);
        List<Pet> pets = petService.findAll();
        if (!pets.isEmpty()) {
            model.addAttribute("pets", pets);
        }
        return "redirect:/user/pets";
    }
}