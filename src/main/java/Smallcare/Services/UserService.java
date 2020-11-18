package Smallcare.Services;

import Smallcare.IServices.IUserService;
import Smallcare.Models.User;
import Smallcare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
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



    public List<User> getAll() {
        return userRepository.findAll();
    }
}
