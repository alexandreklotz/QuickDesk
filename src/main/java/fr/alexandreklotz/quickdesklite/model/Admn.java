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
public class Admn {

    public enum AdmnType{
        TECHNICIAN,
        ADMIN
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
    @JsonView(CustomJsonView.AdmnView.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    private String admnFirstName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    private String admnLastName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    private String admnLogin;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    private String admnPwd;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    private AdmnType admnType;

    ///////////////
    //Constructor//
    ///////////////

    public Admn() {}

    /////////////
    //Relations//
    /////////////

    //An admin/technician can be assigned to multiple tickets but a ticket can only be assigned to a single technician
    @JsonView(CustomJsonView.AdmnView.class)
    @OneToMany(mappedBy = "assignedAdmin")
    private Set<Ticket> assignedTickets;

    //A tech/admin can create multiple comments but can only be assigned to one comment
    @JsonView(CustomJsonView.AdmnView.class)
    @OneToMany(mappedBy = "admin")
    private Set<Comment> comments;

    //A tech/admin can only be part of one team but a team can have multiple tech/admins
    @JsonView(CustomJsonView.AdmnView.class)
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    //A tech/admin can use multiple devices but a device can only be assigned to one user/tech/admn at a time
    @JsonView(CustomJsonView.AdmnView.class)
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

    public String getAdmnFirstName() {
        return admnFirstName;
    }

    public void setAdmnFirstName(String admnFirstName) {
        this.admnFirstName = admnFirstName;
    }

    public String getAdmnLastName() {
        return admnLastName;
    }

    public void setAdmnLastName(String admnLastName) {
        this.admnLastName = admnLastName;
    }

    public String getAdmnLogin() {
        return admnLogin;
    }

    public void setAdmnLogin(String admnLogin) {
        this.admnLogin = admnLogin;
    }

    public String getAdmnPwd() {
        return admnPwd;
    }

    public void setAdmnPwd(String admnPwd) {
        this.admnPwd = admnPwd;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public AdmnType getAdmnType() {
        return admnType;
    }

    public void setAdmnType(AdmnType admnType) {
        this.admnType = admnType;
    }

    public Set<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(Set<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}