package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.UserView.class)
    private int userId;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    private String userFirstName;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    private String userLastName;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    private String userPassword;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    private Date userCreationDate;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    private boolean userIsEnabled;

    /////////////
    //Relations//
    /////////////

    //A user can only be a member of a single group
    @JsonView(CustomJsonView.UserView.class)
    @ManyToOne
    private Team team; //TODO : See why it's impossible to create a user with a specified group when sending a JSON form. IDs don't work.

    //A user can create multiple tickets and a ticket can have multiple affected users
    @JsonView(CustomJsonView.UserView.class)
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "userTickets",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "ticketId") }
    )
    Set<Ticket> ticketsUsr = new HashSet<>();

    //A user can use multiple devices but a device can only be used by one user at a time
    @JsonView(CustomJsonView.UserView.class)
    @OneToMany(mappedBy = "user")
    private List<Device> device;



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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isUserIsEnabled() {
        return userIsEnabled;
    }

    public void setUserIsEnabled(boolean userIsEnabled) {
        this.userIsEnabled = userIsEnabled;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Ticket> getTicketsUsr() {
        return ticketsUsr;
    }

    public void setTicketsUsr(Set<Ticket> ticketsUsr) {
        this.ticketsUsr = ticketsUsr;
    }

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> userDevices) {
        this.device = userDevices;
    }

    public Date getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(Date userCreationDate) {
        this.userCreationDate = userCreationDate;
    }
}
