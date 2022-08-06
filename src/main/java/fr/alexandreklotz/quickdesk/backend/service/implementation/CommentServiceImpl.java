package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.CommentException;
import fr.alexandreklotz.quickdesk.backend.error.TicketException;
import fr.alexandreklotz.quickdesk.backend.model.Comment;
import fr.alexandreklotz.quickdesk.backend.model.Ticket;
import fr.alexandreklotz.quickdesk.backend.repository.CommentRepository;
import fr.alexandreklotz.quickdesk.backend.repository.TicketRepository;
import fr.alexandreklotz.quickdesk.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    public Comment getCommentById(UUID commentid) throws CommentException {
        return commentRepository.findById(commentid).orElseThrow(()
        -> new CommentException(commentid + " doesn't match any existing comment."));
    }

    @Override
    public Comment createNewComment(Comment comment) throws TicketException {

        Optional<Ticket> ticket = ticketRepository.findById(comment.getTicket().getId());
        if(ticket.isEmpty()){
            throw new TicketException("ERROR : Cannot create comment because the ticket " + comment.getTicket().getTicketNumber() + " doesn't exist.");
        }

        //We save the time at which the comment has been created as it will be displayed on the ticket's page.
        comment.setCommentDate(LocalDateTime.now());

        commentRepository.saveAndFlush(comment);

        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) throws CommentException, TicketException {

        Optional<Ticket> linkedTicket = ticketRepository.findById(comment.getTicket().getId());
        if(linkedTicket.isEmpty()){
            throw new TicketException("ERROR : The ticket this comment is linked to doesn't exist or has been deleted.");
        }

        Optional<Comment> updatedComment = commentRepository.findById(comment.getId());
        if(updatedComment.isEmpty()){
            throw new CommentException("ERROR : The specified comment doesn't exist.");
        }

        comment.setHasBeenEdited(true);
        comment.setCommentDateEdited(LocalDateTime.now());

        if(comment.getCommentText() == null){
            throw new CommentException("ERROR : Comment cannot be empty.");
        }

        commentRepository.saveAndFlush(comment);
        return comment;
    }


    @Override
    public void deleteCommentById(UUID commentId) throws CommentException{
        Optional<Comment> deletedComment = commentRepository.findById(commentId);
        if(deletedComment.isEmpty()){
            throw new CommentException("ERROR : This comment doesn't exist or has been deleted.");
        }
        //TODO : Implement a check to verify if the calling user is the author of this comment or if it's an admin. See if it needs to be implemented in other classes.
        commentRepository.deleteById(commentId);
    }
}
