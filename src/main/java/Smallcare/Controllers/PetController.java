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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.Collection;
import java.util.Collections;


@Controller
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    @Value("${upload.path}")
    private String upload_path;

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return ((User) auth.getPrincipal());
    }
    @GetMapping("/pets")
    public String pets(Model model) {
        User user = getCurrentUser();
        if(user == null){
            return "/index";
        }
        Iterable<Pet> petList = userService
                .findById(
                        (user).getId())
                .getPetList();
        if (petList != null) {
            model.addAttribute("pets", petList);
        }
        return "pets";
    }

    @GetMapping("/pets/{id}")
    public String pet(Model model, @PathVariable Long id) {
        Pet pet = petService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (pet != null) {
            model.addAttribute("pet", pet);
        }
        return "pet";
    }

    @GetMapping("/pets/add")
    public String addPet(Model model) {
        model.addAttribute("pet", new Pet());
        return "addPet";
    }

    @PostMapping("/pets/add")
    public String postPet(Model model,
                          @ModelAttribute Pet pet,
                          @RequestParam(value = "file", required = false) MultipartFile file) {
        User user = getCurrentUser();
        if(user == null){
            return "/index";
        }
        if (pet.getName() != null) {
            Pet newPet = petService.save(pet);
            userService.addPet(user, pet);
            if (file != null) {
                try {
                    file.transferTo(new File(upload_path + newPet.getId().toString() + ".png"));
                } catch (IOException ioException){
                    System.out.println("Bad file saving :(");
                }
            }
        }
        Iterable<Pet> petList = userService
                .findById(
                        (user).getId())
                .getPetList();
        if (petList != null) {
            model.addAttribute("pets", petList);
        }
        return "pets";
    }

    @GetMapping("/pets/delete/{id}")
    public String deletePet(Model model, @PathVariable Long id) throws Exception {
        User user = getCurrentUser();
        if(user == null){
            return "/index";
        }
        userService.deletePet(user, petService.findById(id).orElseThrow(Exception::new));
        petService.deleteById(id);
        return pets(model);
    }

}