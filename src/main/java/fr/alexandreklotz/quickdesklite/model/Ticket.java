package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Ticket {

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
    private Long id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.TicketView.class, CustomJsonView.UtilisateurView.class})
    private String ticketTitle;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private String ticketDescription;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @JsonView(CustomJsonView.TicketView.class)
    private Date ticketDateCreated;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @JsonView(CustomJsonView.TicketView.class)
    private Date ticketDateClosed;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketType ticketType;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketPriority ticketPriority;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketStatus ticketStatus;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private TicketCategorization ticketCategorization;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private boolean editableTicket;

    /////////////
    //Relations//
    /////////////

    //A ticket can only be assigned to one user but a user can create multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    //A ticket can be linked to multiple comments but a comment can only be linked to one ticket
    @JsonView(CustomJsonView.TicketView.class)
    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments;

}
