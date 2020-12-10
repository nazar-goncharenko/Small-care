package Smallcare.Models;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class EventComment {

    @Id
    Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "text")
    private String text;

    public EventComment() {
    }

    public EventComment(User user, String text) {
        this.time = LocalDateTime.now();
        this.user = user;
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
