package Smallcare.Controllers;

import Smallcare.Models.User;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;



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
