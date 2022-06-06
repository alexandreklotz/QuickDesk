package fr.alexandreklotz.quickdesklite.service.implementation;

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
    public Comment getCommentById(UUID commentid) {
        Optional<Comment> searchedComment = commentRepository.findById(commentid);
        if(searchedComment.isPresent()){
            return searchedComment.get();
        } else {
            return null;
        }
    }

    @Override
    public Comment createNewComment(Long ticketNbr, Comment comment) {

        Optional<Ticket> currentTicket = ticketRepository.findTicketWithTicketNumber(ticketNbr);
        if(currentTicket.isPresent()){

            //We save the time at which the comment has been created as it will be displayed on the ticket's page.
            comment.setCommentDate(LocalDateTime.now());
            comment.setTicket(currentTicket.get());

            commentRepository.saveAndFlush(comment);
        }

        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) {

        Optional<Comment> updatedComment = commentRepository.findById(comment.getId());
        if(updatedComment.isPresent()){
            if(comment.getTicket().getId() != updatedComment.get().getTicket().getId()){
                //return an error. make sure that this if has a purpose.
            }
            updatedComment.get().setHasBeenEdited(true);
            updatedComment.get().setCommentDate(LocalDateTime.now());
            commentRepository.saveAndFlush(updatedComment.get());
        }
        return updatedComment.get();
    }


    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
