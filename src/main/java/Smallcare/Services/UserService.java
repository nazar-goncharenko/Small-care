package Smallcare.Services;

import Smallcare.Models.*;
import Smallcare.Repositories.EventRepository;
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
    EventService eventService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    public User findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public boolean update(User user){
        User curUser = userRepository.findById(user.getId()).get();
        if (userRepository.findByEmail(user.getEmail()) != null){
            return false;
        }
        if (user.getPassword() != null) {
            curUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if (user.getDescription() != null) {
            curUser.setDescription(user.getDescription());
        }
        if (user.getDistrict() != null) {
            curUser.setDistrict(user.getDistrict());
        }
        if (user.getEmail() != null) {
            curUser.setEmail(user.getEmail());
        }
        if (user.getCity() != null) {
            curUser.setCity(user.getCity());
        }
        if (user.getPhotoUrl() != null) {
            curUser.setPhotoUrl(user.getPhotoUrl());
        }
        if (user.getFirstName() != null) {
            curUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            curUser.setLastName(user.getLastName());
        }
        if (user.getPhoneNumber() != null) {
            curUser.setPhoneNumber(user.getPhoneNumber());
        }
        System.out.println(curUser.getPassword() + "\t" + user.getPassword());
        userRepository.save(curUser);
        return true;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public boolean create(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            Role role = new Role(1L, "ROLE_USER");
            roleRepository.save(role);
            user.setPhotoUrl("defaultUser");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;
    }

    public void addPet(User user, Pet pet) {
        User new_user = userRepository.findById(user.getId()).orElse(new User());
        new_user.addPet(pet);
        userRepository.save(new_user);
    }

    public void addCreatedEvent(User user, Event event){
        User cur_user = userRepository.findById(user.getId()).get();
        event.setCreatorUser(cur_user);
        cur_user.addCreatedEvent(event);
        userRepository.save(cur_user);
    }

    public void rate(Long id, Integer rating){
        ConfirmedEvent confirmedEvent = eventService.getConfirmedEventById(id);
        System.out.println(confirmedEvent.getWorker().getId());
        User worker = userRepository.findById(confirmedEvent.getWorker().getId()).get();
        worker.rate(rating);
        eventService.rateEvent(confirmedEvent);
        userRepository.save(worker);
    }




}
