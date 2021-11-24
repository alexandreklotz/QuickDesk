package fr.alexandreklotz.quickdesk.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    //@JsonView
    private int userId;

    @Column
    private String userFirstName;

    @Column
    private String userLastName;

    /////////////
    //Relations//
    /////////////

    //A user can only be a member of a single group
    @ManyToOne
    private User user;

    //A user can create multiple tickets and a ticket can have multiple affected users
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "userTickets",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "ticket_id") }
    )
    Set<Ticket> ticketsUsr = new HashSet<>();

    //A user can use multiple devices but a device can only be used by one user at a time
    @OneToMany(mappedBy = "userDevice")
    private List<Device> userDevices;



    //Constructor
    public User(){}


    //Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Ticket> getTicketsUsr() {
        return ticketsUsr;
    }

    public void setTicketsUsr(Set<Ticket> ticketsUsr) {
        this.ticketsUsr = ticketsUsr;
    }

    public List<Device> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<Device> userDevices) {
        this.userDevices = userDevices;
    }
}
