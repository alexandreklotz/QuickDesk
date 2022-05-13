package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.repository.CommentRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private TicketRepository ticketRepository;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, TicketRepository ticketRepository){
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public Comment createNewComment(Comment comment) {

        //We save the time at which the comment has been created as it will be displayed on the ticket's page.
        comment.setCommentDate(LocalDateTime.now());

        //Find a way to retrieve the ticket in order to assign the comment to the ticket.

        commentRepository.saveAndFlush(comment);
        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) {
        //Find a way to retrieve the ticket in order to assign the comment to the ticket.

        Optional<Comment> updatedComment = commentRepository.findById(comment.getId());
        if(updatedComment.isPresent()){
            commentRepository.saveAndFlush(updatedComment.get());
        }
        return updatedComment.get();
    }


    @Override
    public void deleteComment(Comment comment) {
        commentRepository.deleteById(comment.getId());
    }
}
