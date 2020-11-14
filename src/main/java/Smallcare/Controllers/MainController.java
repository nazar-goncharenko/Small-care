package Smallcare.Controllers;


import Smallcare.Models.User;
import Smallcare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;



    @RequestMapping("/users")
    public String all(Model model){
        System.out.println("users");
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            model.addAttribute("users", users);
        }
        return "user";
    }

}
