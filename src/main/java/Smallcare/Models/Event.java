package Smallcare.Models;

import javax.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startTime", nullable = false)
    private java.sql.Timestamp startTime;

    @Column(name = "endTime", nullable = false)
    private java.sql.Timestamp endTime;

    @ManyToOne
    private Pet pet;

    @Column(name = "price",nullable = false)
    private Long price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Event() {

    }

    public Long getId() {
        return id;
    }
}
