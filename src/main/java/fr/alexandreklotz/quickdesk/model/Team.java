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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonView(CustomJsonView.TeamView.class)
    private int teamId;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private String teamName;

    @Column
    @JsonView(CustomJsonView.TeamView.class)
    private String teamDescription;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    private Date teamDateCreated;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private boolean isTeamEnabled;

    /////////////
    //Relations//
    /////////////

    //A team has multiple members but a user can only be in one team.
    @JsonView(CustomJsonView.TeamView.class)
    @OneToMany(mappedBy = "team")
    private List<User> teamUsersList;

    //A team can create multiple tickets and a ticket can have multiple affected teams
    @JsonView(CustomJsonView.TeamView.class)
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "teamTickets",
            joinColumns = { @JoinColumn(name = "teamId") },
            inverseJoinColumns = { @JoinColumn(name = "ticketId") }
    )
    private Set<Ticket> ticketsTeams = new HashSet<>();

    //A team can only have one role but a role can have multiple teams
    @JsonView(CustomJsonView.TeamView.class)
    @ManyToOne
    private Role role;




    //Constructor
    public Team(){}


    //Setters and getters
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public List<User> getTeamUsersList() {
        return teamUsersList;
    }

    public void setTeamUsersList(List<User> teamUsersList) {
        this.teamUsersList = teamUsersList;
    }

    public Set<Ticket> getTicketsTeams() {
        return ticketsTeams;
    }

    public void setTicketsTeams(Set<Ticket> ticketsGrp) {
        this.ticketsTeams = ticketsGrp;
    }

    public Date getTeamDateCreated() {
        return teamDateCreated;
    }

    public void setTeamDateCreated(Date teamDateCreated) {
        this.teamDateCreated = teamDateCreated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isIsTeamEnabled() {
        return isTeamEnabled;
    }

    public void setIsTeamEnabled(boolean teamIsEnabled) {
        this.isTeamEnabled = teamIsEnabled;
    }
}
