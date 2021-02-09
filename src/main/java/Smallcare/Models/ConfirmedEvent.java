package Smallcare.Models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ConfirmedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    User creator;

    @OneToOne
    User worker;

    @ManyToMany
    private Set<Pet> pets = new HashSet<Pet>();

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    Status status;

    @Column(name = "startTime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime endTime;

    public ConfirmedEvent(Event event, User worker){
        this.creator = event.getCreatorUser();
        this.description = event.getDescription();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.status = Status.CONFIRMED;
        this.pets.addAll(event.getPets());
        this.worker = worker;
        this.price = event.getPrice();
    }

    public ConfirmedEvent() {
    }

    public ConfirmedEvent(User creator, User worker, Status status) {
        this.creator = creator;
        this.worker = worker;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
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

    public void clearPets(){
        this.pets.clear();
    }
}
