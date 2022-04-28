package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketView.class)
    private Long id;

    @JsonView({CustomJsonView.TicketView.class, CustomJsonView.UtilisateurView.class, CustomJsonView.CommentView.class})
    @Column(nullable = false)
    private String ticketTitle;

    @JsonView(CustomJsonView.TicketView.class)
    @Column(nullable = false)
    private String ticketDescription;

    @JsonView(CustomJsonView.TicketView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime ticketDateCreated;

    @JsonView(CustomJsonView.TicketView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime ticketDateClosed;

    @JsonView(CustomJsonView.TicketView.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime ticketLastModified;

    @JsonView(CustomJsonView.TicketView.class)
    @Column(nullable = false)
    private boolean editableTicket; //TODO : How can it be implemented ? Can the ticket only be edited by a tech/admin once created ? Need to define this

    @JsonView(CustomJsonView.TicketView.class)
    @Column(columnDefinition = "BINARY(16)")
    private UUID assignedAdmin;

    @JsonView(CustomJsonView.TicketView.class)
    @Column
    private String assignedAdminName;

    ///////////////
    //Constructor//
    ///////////////

    public Ticket(){}

    /////////////
    //Relations//
    /////////////

    //A ticket has one category but multiple tickets can be categorized identically
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name = "ticketcategory_id", nullable = false)
    private TicketCategory ticketCategory;

    //A ticket has one type but multiple tickets can have the same type
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name = "tickettype_id", nullable = false)
    private TicketType ticketType;

    //A ticket has one status but multiple tickets can have the same status
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name ="ticketstatus_id", nullable = false)
    private TicketStatus ticketStatus;

    //A ticket has one priority but multiple tickets can have the same priority
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name = "ticketpriority_id", nullable = false)
    private TicketPriority ticketPriority;

    //A ticket can only be assigned to one user but a user can create multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    //A ticket can be linked to multiple comments but a comment can only be linked to one ticket
    @JsonView(CustomJsonView.TicketView.class)
    @OneToMany(mappedBy = "ticket")
    private Set<Comment> comments;

    //A ticket can be assigned to only one queue but a queue can contain multiple tickets
    @JsonView(CustomJsonView.TicketView.class)
    @ManyToOne
    @JoinColumn(name = "ticketQueue_id")
    private TicketQueue ticketQueue;

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

    public LocalDateTime getTicketDateCreated() {
        return ticketDateCreated;
    }

    public void setTicketDateCreated(LocalDateTime ticketDateCreated) {
        this.ticketDateCreated = ticketDateCreated;
    }

    public LocalDateTime getTicketDateClosed() {
        return ticketDateClosed;
    }

    public void setTicketDateClosed(LocalDateTime ticketDateClosed) {
        this.ticketDateClosed = ticketDateClosed;
    }

    public LocalDateTime getTicketLastModified() {
        return ticketLastModified;
    }

    public void setTicketLastModified(LocalDateTime ticketLastModified) {
        this.ticketLastModified = ticketLastModified;
    }

    public boolean isEditableTicket() {
        return editableTicket;
    }

    public void setEditableTicket(boolean editableTicket) {
        this.editableTicket = editableTicket;
    }

    public UUID getAssignedAdmin() {
        return assignedAdmin;
    }

    public void setAssignedAdmin(UUID assignedAdmin) {
        this.assignedAdmin = assignedAdmin;
    }

    public String getAssignedAdminName() {
        return assignedAdminName;
    }

    public void setAssignedAdminName(String assignedAdminName) {
        this.assignedAdminName = assignedAdminName;
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

    public TicketQueue getTicketQueue() {
        return ticketQueue;
    }

    public void setTicketQueue(TicketQueue ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public TicketCategory getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(TicketCategory ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }
}
