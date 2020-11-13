package Smallcare.Controllers;


import Smallcare.Models.User;
import Smallcare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    UserRepository userRepository;



    @GetMapping("/hello")
    public String test(){
        return String.format("Hello world");
    }


    @GetMapping("/users")
    public String all(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user";
    }

}
