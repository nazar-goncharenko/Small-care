package Smallcare.IServices;

import Smallcare.Models.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();

    boolean create(User user);

    User findById(Long id);
}
