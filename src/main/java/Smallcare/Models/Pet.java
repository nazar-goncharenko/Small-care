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

    public Pet() {
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return "../images/"+id.toString()+".jpg";
    }

    public String getDescription() {
        return description;
    }
}
