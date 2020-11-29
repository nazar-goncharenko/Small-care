package Smallcare.Models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(name = "startTime", nullable = false)
    @Column(name = "startTime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime startTime;

    //    @Column(name = "endTime", nullable = false)
    @Column(name = "endTime")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime endTime;

    @ManyToOne
    private Pet pet;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Event() {

    }

    public Event(LocalDateTime startTime, LocalDateTime endTime, Pet pet, Long price, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.pet = pet;
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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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
}
