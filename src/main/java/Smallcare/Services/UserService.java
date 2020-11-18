package Smallcare.Services;

import Smallcare.Models.User;
import Smallcare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean create(String firstName,
                          String lastName,
                          String email,
                          String password,
                          String phoneNumber,
                          String city,
                          String district,
                          String description) {
        System.out.println(userRepository.findByEmail(email));
        if (userRepository.findByEmail(email) == null) {
            userRepository.save(new User(
                    firstName,
                    lastName,
                    email,
                    password,
                    phoneNumber,
                    city,
                    district,
                    description));
        } else {
            return false;
        }
        return true;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
