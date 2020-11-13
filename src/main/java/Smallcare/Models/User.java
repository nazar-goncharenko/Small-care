package Smallcare.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "rating")
    private int rating = 0;

    @Column(name = "description")
    private String description;

    @OneToMany
    private Set<Pet> pets;


    User() {}


}
