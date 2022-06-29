package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.CommentException;
import fr.alexandreklotz.quickdesklite.error.TicketException;
import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.repository.CommentRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.service.CommentService;
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
    public Comment createNewComment(Long ticketNbr, Comment comment) throws TicketException{

        Optional<Ticket> currentTicket = ticketRepository.findTicketWithTicketNumber(ticketNbr);
        if(!currentTicket.isPresent()){
            throw new TicketException("Cannot create a new comment, the specified ticket doesn't exist.");
        }

        //We save the time at which the comment has been created as it will be displayed on the ticket's page.
        comment.setCommentDate(LocalDateTime.now());
        comment.setTicket(currentTicket.get());

        commentRepository.saveAndFlush(comment);

        return comment;
    }

    @Override
    public Comment updateComment(Long ticketNbr, Comment comment) throws CommentException, TicketException {

        Optional<Ticket> linkedTicket = ticketRepository.findTicketWithTicketNumber(ticketNbr);
        if(!linkedTicket.isPresent()){
            throw new TicketException("The ticket this comment is linked to doesn't exist or has been deleted.");
        }

        Optional<Comment> updatedComment = commentRepository.findById(comment.getId());
        if(!updatedComment.isPresent()){
            throw new CommentException("The specified comment doesn't exist.");
        }

        updatedComment.get().setHasBeenEdited(true);
        updatedComment.get().setCommentDate(LocalDateTime.now());

        if(comment.getCommentText() != null){
            updatedComment.get().setCommentText(comment.getCommentText());
        }

        commentRepository.saveAndFlush(updatedComment.get());
        return updatedComment.get();
    }


    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
