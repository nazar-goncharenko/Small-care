package Smallcare.Controllers.my;

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

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/my/pets")
public class PetController {

    @Autowired
    UserService userService;


    @Autowired
    PetService petService;

    @Value("${upload.path}")
    private String upload_path;

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return ((User) auth.getPrincipal());
    }

    @GetMapping
    public String pets(Model model) {
        User user = getCurrentUser();
        Iterable<Pet> petList = userService
                .findById(
                        (user).getId())
                .getPetList();
        if (petList != null) {
            model.addAttribute("pets", petList);
        }
        return "pets";
    }

    @GetMapping("/add")
    public String addPet(Model model) {
        model.addAttribute("pet", new Pet());
        return "addPet";
    }

    @PostMapping("/add")
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

    @GetMapping("/delete/{id}")
    public String deletePet(Model model, @PathVariable Long id) throws Exception {
        User user = getCurrentUser();
        if(user == null){
            return "/index";
        }
        userService.deletePet(user, petService.findById(id).orElseThrow(Exception::new));
        petService.deleteById(id);
        model.addAttribute("pets" , getCurrentUser().getPetList());
        return "pets";
    }
}
