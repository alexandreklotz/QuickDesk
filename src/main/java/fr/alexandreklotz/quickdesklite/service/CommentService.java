package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;


public interface CommentService {

    public Comment createNewComment(Ticket ticket, Comment comment);

    public Comment updateComment(Comment comment);

    public void deleteComment(Comment comment);
}
