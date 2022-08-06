package fr.alexandreklotz.quickdesk.backend.controller.user;

import fr.alexandreklotz.quickdesk.backend.error.CommentException;
import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Comment;
import fr.alexandreklotz.quickdesk.backend.model.Ticket;
import fr.alexandreklotz.quickdesk.backend.service.CommentService;
import fr.alexandreklotz.quickdesk.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //TODO : Implement a password reset method in UtilisateurService/ServiceImpl and here. It has to work for both admins and users and it needs to verify
    // that the calling user is indeed the correct user.

    @GetMapping("/user/ticket/getOpenedTickets")
    public List<Ticket> getOpenedTickets(HttpServletRequest request) throws UtilisateurException{
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ticketService.getOpenedTickets(userLogin);
    }

    @GetMapping("/ticket/{ticketNbr}") //This method will be used by both admins and users. This is why this URL doesn't start with /user
    public Ticket getUserTicket(@PathVariable Long ticketNbr, HttpServletRequest request) throws TicketException, UtilisateurException {
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ticketService.getTicketByNumber(ticketNbr, userLogin);
    }

    @PostMapping("/user/ticket/new")
    public Ticket createUserTicket(@RequestBody Ticket ticket) throws DefaultValueException {
        return ticketService.createUserTicket(ticket);
    }

    @PostMapping("/ticket/{ticketNbr}/addComment") //This method will be used by both admins and users. This is why this URL doesn't start with /user
    public Comment addCommentToTicket(@RequestBody Comment comment) throws TicketException {
        return commentService.createNewComment(comment);
    }

    @PutMapping("/ticket/{ticketNbr}/commentUpdate") //This method will be used by both admins and users. This is why this URL doesn't start with /user
    public Comment updateExistingComment(@RequestBody Comment comment) throws TicketException, CommentException {
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/comment/id/{commentId}/delete") //This method will be used by both admins and users. This is why this URL doesn't start with /user
    public void deleteCommentById(@PathVariable UUID commentId) throws CommentException {
        commentService.deleteCommentById(commentId);
    }

}
