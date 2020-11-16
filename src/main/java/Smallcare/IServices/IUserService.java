package Smallcare.IServices;

import Smallcare.Models.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();

    boolean create(String firstName,
                   String lastName,
                   String email,
                   String password,
                   String phoneNumber,
                   String city,
                   String district,
                   String description);
}
