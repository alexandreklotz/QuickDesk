package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.CommentException;
import fr.alexandreklotz.quickdesklite.error.TicketException;
import fr.alexandreklotz.quickdesklite.model.Comment;

import java.util.UUID;


public interface CommentService {

    public Comment createNewComment(Long ticketNbr, Comment comment) throws TicketException;

    public Comment updateComment(Comment comment) throws CommentException;

    public void deleteComment(Comment comment);

    public Comment getCommentById(UUID commentid) throws CommentException;
}
