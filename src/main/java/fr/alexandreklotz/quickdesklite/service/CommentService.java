package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;

import java.util.UUID;


public interface CommentService {

    public Comment createNewComment(Long ticketNbr, Comment comment);

    public Comment updateComment(Comment comment);

    public void deleteComment(Comment comment);

    public Comment getCommentById(UUID commentid);
}
