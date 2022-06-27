package fr.alexandreklotz.quickdesklite.controller.user;

import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.service.CommentService;
import fr.alexandreklotz.quickdesklite.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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

    //Methods for tickets. Will be used to retrieve and create tickets.
    /*
    @PostMapping("/ticket/new")
    public ResponseEntity<Ticket> createUserTicket(@RequestBody Ticket ticket){
        ticketService.createUserTicket(ticket);
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping("/ticket/{ticketNbr}")
    public ResponseEntity<Ticket> getUserTicket(@PathVariable Long ticketNbr, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ResponseEntity.ok(ticketService.getTicketByNumber(ticketNbr, userLogin));
    }


    //Methods for comments. Will be used to create, retrieve and delete comments.

    @PostMapping("/ticket/{ticketNbr}/addComment")
    public ResponseEntity<Comment> addCommentOnTicket(@PathVariable Long ticketNbr, @RequestBody Comment comment){
        return ResponseEntity.ok(commentService.createNewComment(ticketNbr, comment));
    }

    @PostMapping("/{commentId}/update")
    public ResponseEntity<Comment> updateExistingComment(@RequestBody Comment comment){
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Comment> deleteExistingComment(@RequestBody Comment comment){
        return ResponseEntity.ok(commentServiceImpl.deleteComment(comment));
    }*/

}
