package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    public enum UserType {
        USER,
        VIP
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.UtilisateurView.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    private String utilFirstName;

    @Column(nullable = false)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    private String utilLastName;

    @Column(nullable = false, unique = true)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.DeviceView.class, CustomJsonView.CommentView.class})
    private String utilLogin;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String utilPwd;

    @Column(nullable = false, unique = true)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String utilMailAddr;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

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
    private Set<Ticket> createdTickets;

    //A user, whatever his role is, can create multiple comments but a comment can only be created/linked by/to one user.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @OneToMany(mappedBy = "utilisateur")
    private Set<Comment> postedComments;

    //A user can only be assigned to one device but a device can have multiple users
    @JsonView(CustomJsonView.UtilisateurView.class)
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    /////////////////////
    //Getters & Setters//
    /////////////////////


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getUtilMailAddr() {
        return utilMailAddr;
    }

    public void setUtilMailAddr(String utilMailAddr) {
        this.utilMailAddr = utilMailAddr;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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

    public Set<Ticket> getCreatedTickets() {
        return createdTickets;
    }

    public void setCreatedTickets(Set<Ticket> tickets) {
        this.createdTickets = tickets;
    }

    public Set<Comment> getPostedComments() {
        return postedComments;
    }

    public void setPostedComments(Set<Comment> comments) {
        this.postedComments = comments;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
