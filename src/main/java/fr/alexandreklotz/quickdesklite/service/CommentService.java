package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Comment;

import java.util.List;

public interface CommentService {

    public Comment createNewComment(Comment comment);

    public Comment updateComment(Comment comment);

    public void deleteComment(Comment comment);
}
