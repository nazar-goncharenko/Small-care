package Smallcare.Services;

import Smallcare.IServices.IUserService;
import Smallcare.Models.Role;
import Smallcare.Models.User;
import Smallcare.Repositories.RoleRepository;
import Smallcare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
            Role role = new Role(1L, "ROLE_USER");
            roleRepository.save(role);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(role));
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user  = userRepository.findByEmail(email);
        if ( user == null){
            throw new UsernameNotFoundException("Email not found");
        }

        return user;
    }
}
