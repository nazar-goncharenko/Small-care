package Smallcare.Controllers.my;

import Smallcare.Models.Pet;
import Smallcare.Models.User;
import Smallcare.Services.EventService;
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
import java.util.UUID;

@Controller
@RequestMapping("/my/pets")
public class PetController {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    PetService petService;

    @Value("${upload.path}")
    private String upload_path;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return userService.findById(((User) auth.getPrincipal()).getId());
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
        if(file != null){
            if(!file.isEmpty()){
                pet.setPhotoUrl(UUID.randomUUID().toString());
            } else {
                pet.setPhotoUrl("defaultPet");
            }
        } else {
            pet.setPhotoUrl("defaultPet");
        }
        if (pet.getName() != null) {
            pet.setUser(user);
            Pet newPet = petService.save(pet);
            userService.addPet(user, pet);
            if (file != null) {
                try {
                    if(!file.isEmpty()){
                        file.transferTo(new File(upload_path + newPet.getPhotoUrl() + ".png"));
                    }
                } catch (IOException ioException){
                    System.out.println("Error: " + ioException.getLocalizedMessage());
                }
            }
        }
        return pets(model);
    }


    @PostMapping("/{id}/delete")
    public String deletePet(Model model, @PathVariable Long id) {
        User user = getCurrentUser();
        if(user == null){
            return pets(model);
        }
        model.addAttribute("pets" , getCurrentUser().getPetList());
        if (petService.findById(id).isEmpty()) {
            return pets(model);
        }
        petService.deletePet(user, petService.findById(id).get());
        return pets(model);
    }
}
