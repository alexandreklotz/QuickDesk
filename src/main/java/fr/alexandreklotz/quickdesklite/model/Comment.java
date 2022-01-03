package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @Column(nullable = false)
    private String commentText;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @Column(nullable = false)
    private Date commentDate;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @Column(nullable = true)
    private Date commentLastModification;

    //TODO : Check if this variable will be useful.
    @Column(nullable = false)
    private boolean editableComment;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Date getCommentLastModification() {
        return commentLastModification;
    }

    public void setCommentLastModification(Date commentLastModification) {
        this.commentLastModification = commentLastModification;
    }

    public boolean isEditableComment() {
        return editableComment;
    }

    public void setEditableComment(boolean editableComment) {
        this.editableComment = editableComment;
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
}
