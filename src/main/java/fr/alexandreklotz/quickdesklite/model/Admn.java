package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Admn {

    public enum AdmnType {
        ADMIN
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @JsonView({CustomJsonView.AdmnView.class, CustomJsonView.CommentView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    @Column(nullable = false)
    private String admnFirstName;

    @JsonView({CustomJsonView.AdmnView.class, CustomJsonView.CommentView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    @Column(nullable = false)
    private String admnLastName;

    @JsonView({CustomJsonView.AdmnView.class, CustomJsonView.DeviceView.class})
    @Column(nullable = false)
    private String admnLogin;

    @JsonView(CustomJsonView.AdmnView.class)
    @Column(nullable = false)
    private String admnPwd;

    @JsonView(CustomJsonView.AdmnView.class)
    @Column(nullable = false)
    private String admnMailaddr;

    @Column(nullable = false)
    @JsonView(CustomJsonView.AdmnView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonView(CustomJsonView.AdmnView.class)
    @Column(nullable = false)
    private AdmnType admnType;

    ///////////////
    //Constructor//
    ///////////////

    public Admn(){}

    /////////////
    //Relations//
    /////////////

    //A tech/admin can be assigned to multiple tickets but a ticket can only be assigned to a single tech/admin
    @JsonView(CustomJsonView.AdmnView.class)
    @OneToMany(mappedBy = "admn")
    private Set<Ticket> tickets;

    //A tech/admin can be member of a single team but teams can have multiple techs/admins as members
    @JsonView(CustomJsonView.AdmnView.class)
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    //A tech/admin can be assigned to one device at a time but a device can be assigned to multiple techs/admins
    @JsonView(CustomJsonView.AdmnView.class)
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    //A tech/admin can create multiple comments
    @JsonView(CustomJsonView.AdmnView.class)
    @OneToMany(mappedBy = "admn")
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

    public String getAdmnMailaddr() {
        return admnMailaddr;
    }

    public void setAdmnMailaddr(String admnMailaddr) {
        this.admnMailaddr = admnMailaddr;
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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
