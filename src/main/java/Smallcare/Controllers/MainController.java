package Smallcare.Controllers;


import Smallcare.Models.User;
import Smallcare.Repositories.UserRepository;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping()
@Controller
public class MainController {

    @Autowired
    UserService userService;


    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @PostMapping("/user")
    public String createUser(Model model,
                             @RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "lastName") String lastName,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "phoneNumber") String phoneNumber,
                             @RequestParam(value = "city", required = false) String city,
                             @RequestParam(value = "district", required = false) String district,
                             @RequestParam(value = "description", required = false) String description){
        userService.create(firstName, lastName, email, password, phoneNumber, city, district, description);
        return "redirect:/";
    }


    @GetMapping("/users")
    public String all(Model model){
        List<User> users = userService.getAll();
        if (!users.isEmpty()) {
            model.addAttribute("users", users);
        }
        return "user";
    }

}
