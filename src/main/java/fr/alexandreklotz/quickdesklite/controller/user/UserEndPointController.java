package fr.alexandreklotz.quickdesklite.controller.user;

import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.service.implementation.CommentServiceImpl;
import fr.alexandreklotz.quickdesklite.service.implementation.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class UserEndPointController {

    private TicketServiceImpl ticketServiceImpl;
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    UserEndPointController(TicketServiceImpl ticketServiceImpl, CommentServiceImpl commentServiceImpl){
        this.ticketServiceImpl = ticketServiceImpl;
        this.commentServiceImpl = commentServiceImpl;
    }

    ///////////////
    // Endpoints //
    ///////////////

    //Methods for tickets. Will be used to retrieve and create tickets.

    @PostMapping("/ticket/new")
    public ResponseEntity<Ticket> createUserTicket(@RequestBody Ticket ticket){
        ticketServiceImpl.createUserTicket(ticket);
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping("/ticket/{ticketNbr}")
    public ResponseEntity<Ticket> getUserTicket(@PathVariable Long ticketNbr, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return ResponseEntity.ok(ticketServiceImpl.getTicketByNumber(ticketNbr, userLogin));
    }


    //Methods for comments. Will be used to create, retrieve and delete comments.

    @PostMapping("/ticket/{ticketNbr}/addComment")
    public ResponseEntity<Comment> addCommentOnTicket(@PathVariable Long ticketNbr, @RequestBody Comment comment){
        return ResponseEntity.ok(commentServiceImpl.createNewComment(ticketNbr, comment));
    }

    @PostMapping("/{commentId}/update")
    public ResponseEntity<Comment> updateExistingComment(@RequestBody Comment comment){
        return ResponseEntity.ok(commentServiceImpl.updateComment(comment));
    }

    /*@DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Comment> deleteExistingComment(@RequestBody Comment comment){
        return ResponseEntity.ok(commentServiceImpl.deleteComment(comment));
    }*/

}
