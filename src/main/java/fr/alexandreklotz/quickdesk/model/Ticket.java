package fr.alexandreklotz.quickdesk.model;

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
    @Column
    //@JsonView
    private int ticketId;

    @Column
    private String ticketTitle;

    @Column
    private String ticketDescription;

    @Column
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date ticketDateCreated;

    @Column
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date ticketDateClosed;

    /////////////
    //Relations//
    /////////////

    //A ticket can have multiple affected users and a user can create multiple tickets
    @ManyToMany(mappedBy = "ticketsUsr")
    private Set<User> users = new HashSet<>();

    //A ticket can have multiple affected groups and a group can create multiple tickets
    @ManyToMany(mappedBy = "ticketsGrp")
    private Set<Group> groups = new HashSet<>();

    //A ticket can have multiple affected devices and a device can be affected to multiple tickets
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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
}
