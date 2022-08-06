package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.CommentException;
import fr.alexandreklotz.quickdesk.backend.error.TicketException;
import fr.alexandreklotz.quickdesk.backend.model.Comment;

import java.util.UUID;


public interface CommentService {

    public Comment createNewComment(Comment comment) throws TicketException;

    public Comment updateComment(Comment comment) throws CommentException, TicketException;

    public void deleteCommentById(UUID commentId) throws CommentException;

    public Comment getCommentById(UUID commentid) throws CommentException;
}
