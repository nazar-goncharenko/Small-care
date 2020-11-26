package Smallcare.Controllers;

import Smallcare.IServices.IUserService;
import Smallcare.Models.User;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping
    public String createUser(Model model,@ModelAttribute User user){
        userService.create(user);
        return "redirect:/";
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
