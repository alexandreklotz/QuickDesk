package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class, CustomJsonView.UtilisateurView.class})
    @Column(nullable = false)
    private String commentText;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime commentDate;

    @JsonView({CustomJsonView.CommentView.class,CustomJsonView.TicketView.class})
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime commentLastModification;

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

    //A comment can be created by a single user at a time only.
    @JsonView(CustomJsonView.CommentView.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;

    //A comment can be created by a single admin at a time only
    @JsonView(CustomJsonView.CommentView.class)
    @ManyToOne
    @JoinColumn(name = "admn_id")
    private Admn admn;


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

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public LocalDateTime getCommentLastModification() {
        return commentLastModification;
    }

    public void setCommentLastModification(LocalDateTime commentLastModification) {
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

    public Admn getAdmn() {
        return admn;
    }

    public void setAdmn(Admn admn) {
        this.admn = admn;
    }
}
