package fr.alexandreklotz.quickdesk.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class, CustomJsonView.UtilisateurView.class})
    @Column(nullable = false)
    private String commentText;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime commentDate;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime commentDateEdited;

    @JsonView({CustomJsonView.CommentView.class, CustomJsonView.TicketView.class})
    @Column
    private boolean hasBeenEdited;

    ///////////////
    //Constructor//
    ///////////////

    public Comment(){}

    /////////////
    //Relations//
    /////////////

    //A comment can only be linked to one ticket but a ticket can have multiple comments
    @JsonView(CustomJsonView.CommentView.class)
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    //A comment can be created by a single user only.
    @JsonView(CustomJsonView.CommentView.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;

    /////////////////////
    //Getters & Setters//
    /////////////////////


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public LocalDateTime getCommentDateEdited() {
        return commentDateEdited;
    }

    public void setCommentDateEdited(LocalDateTime commentDateEdited) {
        this.commentDateEdited = commentDateEdited;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isHasBeenEdited() {
        return hasBeenEdited;
    }

    public void setHasBeenEdited(boolean hasBeenEdited) {
        this.hasBeenEdited = hasBeenEdited;
    }
}
