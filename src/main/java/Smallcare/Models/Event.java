package Smallcare.Models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startTime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime endTime;

    @ManyToMany
    private Set<Pet> pets = new HashSet<Pet>();

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    Set <EventComment> eventComments = new HashSet<EventComment>();

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    User creatorUser;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<User> signedUsers = new HashSet<User>();

    public Event() {

    }

    public Event(LocalDateTime startTime, LocalDateTime endTime, Long price, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.description = description;
        this.status = Status.REQUEST;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }
    
    public void addComment(EventComment eventComment){
        this.eventComments.add(eventComment);
    }

    public void addPet(Pet pet){
        this.pets.add(pet);
    }

    public void addSingedUser(User user){
        this.signedUsers.add(user);
    }

    public void clearPets(){
        this.pets.clear();
    }

    public void clearSinged(){
        for (User user: this.signedUsers
             ) {
            user.deleteSignedEvent(this);
        }
        this.signedUsers.clear();
    }

    public Set<User> getSignedUsers() {
        return signedUsers;
    }

    public void setSignedUsers(Set<User> signedUsers) {
        this.signedUsers = signedUsers;
    }

    public Set<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(Set<EventComment> eventComments) {
        this.eventComments = eventComments;
    }
}
