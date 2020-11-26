package Smallcare.Models;

import javax.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "description")
    private String description;

    public Pet() {}

    public Pet(String name, String description, String photoUrl) {
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
