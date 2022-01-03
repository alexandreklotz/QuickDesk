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
    private boolean editableTicket; //TODO : How can it be implemented ? Can the ticket only be edited by a tech/admin one created ? Need to define this

    ///////////////
    //Constructor//
    ///////////////

    public Ticket(){}

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

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketCategorization getTicketCategorization() {
        return ticketCategorization;
    }

    public void setTicketCategorization(TicketCategorization ticketCategorization) {
        this.ticketCategorization = ticketCategorization;
    }

    public boolean isEditableTicket() {
        return editableTicket;
    }

    public void setEditableTicket(boolean editableTicket) {
        this.editableTicket = editableTicket;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
