package Smallcare.Controllers;

import Smallcare.Models.User;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return ((User) auth.getPrincipal());
    }

    @PostMapping
    public String createUser(Model model,@ModelAttribute User user){
        if ( userService.create(user)){
            return "index";
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("user",user);
            return "signUp";

        }
    }

    @PostMapping("/{id}")
    public String updateUser(Model model,@ModelAttribute User user,@PathVariable Long id){
        User curUser = getCurrentUser();
        System.out.println(user.getFirstName() + "\t" + id);
        if (curUser.getId() != id){
            return "user";
        }
        if (userService.update(user)){
            return "user";
        }
        return "index";

    }

    @GetMapping
    public String all(Model model){
        model.addAttribute("user", new User());
        List<User> users = userService.getAll();
        if (!users.isEmpty()) {
            model.addAttribute("users", users);
        }
        return "user";
    }

    @GetMapping("/{id}")
    public String userById(Model model, @PathVariable Long id){
        model.addAttribute("user", new User());
        User user = userService.findById(id);
        if(user != null){
            model.addAttribute("users" , user);
            return "user";
        }
        else
        {
            return "redirect:/";
        }
    }
}
