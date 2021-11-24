package fr.alexandreklotz.quickdesk.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    //@JsonView
    private int groupId;

    @Column
    private String groupName;

    @Column
    private String groupDescription;

    /////////////
    //Relations//
    /////////////

    //A group has multiple members but a user can only be in one group.
    @OneToMany(mappedBy = "user")
    private List<User> groupUsersList;

    //A group can create multiple tickets and a ticket can have multiple affected groups
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "groupTickets",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "ticket_id") }
    )
    private Set<Ticket> ticketsGrp = new HashSet<>();




    //Constructor
    public Group(){}


    //Setters and getters
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public List<User> getGroupUsersList() {
        return groupUsersList;
    }

    public void setGroupUsersList(List<User> groupUsersList) {
        this.groupUsersList = groupUsersList;
    }

    public Set<Ticket> getTicketsGrp() {
        return ticketsGrp;
    }

    public void setTicketsGrp(Set<Ticket> ticketsGrp) {
        this.ticketsGrp = ticketsGrp;
    }
}
