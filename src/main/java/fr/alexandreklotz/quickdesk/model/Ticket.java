package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    //TODO : Create a String variable/embedabble/class to add comments in a ticket. See how we can add multiple comments (and if the concerned user and tech/team can aswell.
    //TODO : Manage images implementation => Images in the ticket desc or comments.

    //TODO : ENUM for ticket status and types !! -> Created, testing needed
    public enum TicketStatus {
        OPEN,
        ONHOLD,
        ONGOING,
        REVIEW,
        CLOSED
    }

    public enum TicketType {
        REQUEST,
        INCIDENT,
        CHANGE,
        ISSUE
    }

    public enum TicketCategorization {
        TOCATEGORIZE,
        NETWORK,
        HARDWARE,
        SOFTWARE,
        SECURITY,
        LICENSE,
        CONTRACT
    }

    public enum TicketPriority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

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

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketStatus ticketStatus;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketType ticketType;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketCategorization ticketCategorization;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketPriority ticketPriority;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private boolean editableTicket;

    /////////////
    //Relations//
    /////////////

    //A ticket can have one affected user and a user can create multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    /*@ManyToMany(mappedBy = "ticket")
    private Set<User> user;*/

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    //A ticket can have one affected team and a team can create multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    /*@ManyToMany(mappedBy = "ticket")
    private Set<Team> team;*/

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    //A ticket can have one affected device and a device can be affected to multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    /*@ManyToMany(mappedBy = "ticket")
    private Set<Device> device;*/

    @ManyToOne
    @JoinColumn(name = "deviceId")
    private Device device;

}
