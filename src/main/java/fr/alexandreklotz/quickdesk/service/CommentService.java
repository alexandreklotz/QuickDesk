package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.CommentException;
import fr.alexandreklotz.quickdesk.error.TicketException;
import fr.alexandreklotz.quickdesk.model.Comment;

import java.util.UUID;


public interface CommentService {

    public Comment createNewComment(Comment comment) throws TicketException;

    public Comment updateComment(Comment comment) throws CommentException, TicketException;

    public void deleteCommentById(UUID commentId) throws CommentException;

    public Comment getCommentById(UUID commentid) throws CommentException;
}
