package Smallcare.Controllers.my;

import Smallcare.Models.User;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
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

    @GetMapping("/profile")
    public String myProfile(Model model){
        model.addAttribute("user" , getCurrentUser());
        model.addAttribute("owner" , getCurrentUser());
        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(Model model, @PathVariable Long id){
        model.addAttribute("user" , getCurrentUser());
        User user = userService.findById(id);
        if(user != null){
            model.addAttribute("owner" , user);
        }
        return "profile";
    }

}
