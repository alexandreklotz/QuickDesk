package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private int ticketId;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private String ticketTitle;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private String ticketDescription;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date ticketDateCreated;

    @Column
    @JsonView(CustomJsonView.TicketView.class)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date ticketDateClosed;

    /////////////
    //Relations//
    /////////////

    //A ticket can have multiple affected users and a user can create multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToMany(mappedBy = "ticketsUsr")
    private Set<User> users = new HashSet<>();

    //A ticket can have multiple affected teams and a team can create multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToMany(mappedBy = "ticketsTeams")
    private Set<Team> tkteams = new HashSet<>();

    //A ticket can have multiple affected devices and a device can be affected to multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToMany(mappedBy = "ticketsDev")
    private Set<Device> devices = new HashSet<>();


    //Constructor
    public Ticket(){}


    //Setters and getters

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public Date getTicketDateCreated() {
        return ticketDateCreated;
    }

    public void setTicketDateCreated(Date ticketDateCreated) {
        this.ticketDateCreated = ticketDateCreated;
    }

    public Date getTicketDateClosed() {
        return ticketDateClosed;
    }

    public void setTicketDateClosed(Date ticketDateClosed) {
        this.ticketDateClosed = ticketDateClosed;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Team> getteams() {
        return tkteams;
    }

    public void setteams(Set<Team> teams) {
        this.tkteams = teams;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<Team> getTkteams() {
        return tkteams;
    }

    public void setTkteams(Set<Team> teams) {
        this.tkteams = teams;
    }
}
