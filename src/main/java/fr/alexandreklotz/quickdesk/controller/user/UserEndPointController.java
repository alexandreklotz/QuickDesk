package fr.alexandreklotz.quickdesk.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.CommentException;
import fr.alexandreklotz.quickdesk.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.error.TicketException;
import fr.alexandreklotz.quickdesk.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.model.Comment;
import fr.alexandreklotz.quickdesk.model.Ticket;
import fr.alexandreklotz.quickdesk.service.CommentService;
import fr.alexandreklotz.quickdesk.service.TicketService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/user/ticket/getOpenedTickets")
    public List<Ticket> getOpenedTickets(HttpServletRequest request) throws UtilisateurException{
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ticketService.getOpenedTickets(userLogin);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/all/ticket/{ticketNbr}")
    public Ticket getUserTicket(@PathVariable Long ticketNbr, HttpServletRequest request) throws TicketException, UtilisateurException {
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ticketService.getTicketByNumber(ticketNbr, userLogin);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @PostMapping("/user/ticket/create")
    public Ticket createUserTicket(@RequestBody Ticket ticket) throws DefaultValueException {
        return ticketService.createUserTicket(ticket);
    }

    @JsonView(CustomJsonView.CommentView.class)
    @PostMapping("/all/ticket/{ticketNbr}/addComment")
    public Comment addCommentToTicket(@RequestBody Comment comment) throws TicketException {
        return commentService.createNewComment(comment);
    }

    @JsonView(CustomJsonView.CommentView.class)
    @PutMapping("/all/ticket/{ticketNbr}/commentUpdate")
    public Comment updateExistingComment(@RequestBody Comment comment) throws TicketException, CommentException {
        return commentService.updateComment(comment);
    }

    @JsonView(CustomJsonView.CommentView.class)
    @DeleteMapping("/all/comment/id/{commentId}/delete")
    public void deleteCommentById(@PathVariable UUID commentId) throws CommentException {
        commentService.deleteCommentById(commentId);
    }

}
