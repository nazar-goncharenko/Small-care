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

    public User findById(Long id){
        if(userRepository.findById(id).isPresent())
        {
            return userRepository.findById(id).get();
        }
        else {
            return null;
        }
    }

    public boolean create(User user){
        if( userRepository.findByEmail(user.getEmail()) == null)
        {
            userRepository.save(user);
            return true;
        }
        else
            {
            return false;
        }
    }

    public boolean deleteUser(String firstName,
                              String lastName,
                              String email,
                              String password,
                              String phoneNumber,
                              String city,
                              String district,
                              String description){
        User user = userRepository.findByEmail(email);
        if ( user != null) {
            userRepository.delete(user);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String phoneNumber,
                           String city,
                           String district,
                           String description){
        try {
            User user = userRepository.findByEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setCity(city);
            user.setDistrict(district);
            user.setDescription(description);
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
