package Smallcare.Controllers.my;

import Smallcare.Models.User;
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
@RequestMapping("/my")
public class UserController {

    @Autowired
    UserService userService;

    @Value("${upload.path}")
    private String upload_path;

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return userService.findById(((User) auth.getPrincipal()).getId());
    }

    @GetMapping("/profile")
    public String myProfile(Model model){
        model.addAttribute("user" , getCurrentUser());
        model.addAttribute("newUser" , new User());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Model model,
                                @ModelAttribute User user,
                                @RequestParam(value = "file", required = false) MultipartFile file){
        System.out.println(file == null);
        if(file != null && !file.isEmpty()){
            user.setPhotoUrl(UUID.randomUUID().toString());
        }
        user.setId(getCurrentUser().getId());
        if(userService.update(user)){
            if (file != null) {
                System.out.println(file.isEmpty() ? "fuck" : "Oh)");
                try {
                    if(!file.isEmpty()){
                        file.transferTo(new File(upload_path + user.getPhotoUrl() + ".png"));
                    }
                } catch (IOException ioException){
                    System.out.println("Error: " + ioException.getLocalizedMessage());
                }
            }
        } else{
            model.addAttribute("error", true);
        }
        return myProfile(model);
    }
}
