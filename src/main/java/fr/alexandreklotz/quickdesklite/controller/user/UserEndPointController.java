package fr.alexandreklotz.quickdesklite.controller.user;

import fr.alexandreklotz.quickdesklite.error.CommentException;
import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.service.CommentService;
import fr.alexandreklotz.quickdesklite.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class UserEndPointController {

    private TicketService ticketService;
    private CommentService commentService;

    @Autowired
    UserEndPointController(TicketService ticketService, CommentService commentService){
        this.ticketService = ticketService;
        this.commentService = commentService;
    }

    ///////////////
    // Endpoints //
    ///////////////

    @GetMapping("/user/ticket/getOpenedTickets")
    public List<Ticket> getOpenedTickets(HttpServletRequest request) throws UtilisateurException{
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ticketService.getOpenedTickets(userLogin);
    }

    @GetMapping("/user/ticket/{ticketNbr}")
    public Ticket getUserTicket(@PathVariable Long ticketNbr, HttpServletRequest request) throws TicketException, UtilisateurException {
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ticketService.getTicketByNumber(ticketNbr, userLogin);
    }

    @PostMapping("/user/ticket/new")
    public Ticket createUserTicket(@RequestBody Ticket ticket) throws DefaultValueException {
        return ticketService.createUserTicket(ticket);
    }

    @PostMapping("/user/ticket/{ticketNbr}/addComment")
    public Comment addCommentToTicket(@PathVariable Long ticketNbr, @RequestBody Comment comment) throws TicketException {
        return commentService.createNewComment(ticketNbr, comment);
    }

    @PostMapping("/user/ticket/{ticketNbr}/commentUpdate")
    public Comment updateExistingComment(@PathVariable Long ticketNbr, @RequestBody Comment comment) throws TicketException, CommentException {
        return commentService.updateComment(ticketNbr, comment);
    }

    @DeleteMapping("/user/comment/{commentId}/delete")
    public void deleteComment(@RequestBody Comment comment){
        commentService.deleteComment(comment);
    }

}
