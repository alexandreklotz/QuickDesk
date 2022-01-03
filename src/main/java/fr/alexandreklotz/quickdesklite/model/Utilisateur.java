package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    public enum UserType {
        USER,
        TECHNICIAN,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    private String utilFirstName;

    @Column(nullable = false)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    private String utilLastName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String utilLogin;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String utilPwd;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    @JsonView(CustomJsonView.UtilisateurView.class)
    private Date creationDate;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private UserType userType;

    ///////////////
    //Constructor//
    ///////////////

    public Utilisateur(){}

    /////////////
    //Relations//
    /////////////

    //A user can be part of only one team but a team can have multiple users
    @JsonView(CustomJsonView.UtilisateurView.class)
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    //A user can create multiple tickets but a ticket can only be assigned to one user
    @JsonView(CustomJsonView.UtilisateurView.class)
    @OneToMany(mappedBy = "utilisateur")
    private Set<Ticket> tickets;

    //A user, whatever his role is, can create multiple comments but a comment can only be created/linked by/to one user.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @OneToMany(mappedBy = "utilisateur")
    private Set<Comment> comments;

    /////////////////////
    //Getters & Setters//
    /////////////////////


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtilFirstName() {
        return utilFirstName;
    }

    public void setUtilFirstName(String utilFirstName) {
        this.utilFirstName = utilFirstName;
    }

    public String getUtilLastName() {
        return utilLastName;
    }

    public void setUtilLastName(String utilLastName) {
        this.utilLastName = utilLastName;
    }

    public String getUtilLogin() {
        return utilLogin;
    }

    public void setUtilLogin(String utilLogin) {
        this.utilLogin = utilLogin;
    }

    public String getUtilPwd() {
        return utilPwd;
    }

    public void setUtilPwd(String utilPwd) {
        this.utilPwd = utilPwd;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
