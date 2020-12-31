package Smallcare.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "lastName")
    private String lastName;

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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Pet> petList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserComment> userComments = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> createdEvents = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> signedEvents = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String photoUrl, String phoneNumber, String city, String district, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.district = district;
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getFirstName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getDescription() {
        return description;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Set<Pet> getPetList() {
        return petList;
    }

    public void setPetList(Set<Pet> petList) {
        this.petList = petList;
    }

    public void addPet(Pet pet) {
        this.petList.add(pet);
    }

    public void deletePet(Pet pet) {
        this.petList.remove(pet);
    }

    public Set<UserComment> getUserComments() {
        return userComments;
    }

    public void setUserComments(Set<UserComment> userComments) {
        this.userComments = userComments;
    }

    public void addComment(UserComment userComment) {
        this.userComments.add(userComment);
    }

    public Set<Event> getCreatedEvents() {
        return this.createdEvents;
    }

    public void setCreatedEvents(Set<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public void addCreatedEvent(Event event) {
        this.createdEvents.add(event);
    }

    public void deleteCreatedEvent(Event event) {
        this.createdEvents.remove(event);
    }

    public Set<Event> getSignedEvents() {
        return this.signedEvents;
    }

    public void setSignedEvents(Set<Event> signedEvents) {
        this.signedEvents = signedEvents;
    }

    public void addSignedEvent(Event event) {
        this.signedEvents.add(event);
    }

    public void deleteSignedEvent(Event event) {
        this.signedEvents.remove(event);
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
