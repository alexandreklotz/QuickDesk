package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Admn;
import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.AdmnRepository;
import fr.alexandreklotz.quickdesklite.repository.CommentRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class CommentController {

    private TicketRepository ticketRepository;
    private CommentRepository commentRepository;
    private UtilisateurRepository utilisateurRepository;
    private AdmnRepository admnRepository;

    @Autowired
    CommentController(TicketRepository ticketRepository, CommentRepository commentRepository, UtilisateurRepository utilisateurRepository, AdmnRepository admnRepository){
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.admnRepository = admnRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.CommentView.class)
    @GetMapping("/comments/all")
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentRepository.findAll());
    }


    @JsonView(CustomJsonView.CommentView.class)
    @GetMapping("/comments/{commentid}")
    public ResponseEntity<Comment> getSpecifiedComment (@PathVariable Long commentid){

        Optional<Comment> commentBdd = commentRepository.findById(commentid);

        if(commentBdd.isPresent()){
            return ResponseEntity.ok(commentBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.CommentView.class)
    @PostMapping("/ticket/{ticketId}/comment/new/{userId}")
    public ResponseEntity<String> newComment (@PathVariable Long ticketId,
                            @PathVariable UUID userId,
                            @RequestBody Comment comment){

        /*Optional<Utilisateur> userBdd = utilisateurRepository.findById(userid);
        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketid);

        if(ticketBdd.isPresent() && userBdd.isPresent()){
            comment.setCommentDate(LocalDateTime.now());
            comment.setTicket(ticketBdd.get());
            comment.setUtilisateur(userBdd.get());
            commentRepository.saveAndFlush(comment);
        }*/

        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketId);

        if(ticketBdd.isPresent()){

            Optional<Utilisateur> userBdd = utilisateurRepository.findById(userId);

            if(userBdd.isPresent()){
                comment.setCommentDate(LocalDateTime.now());
                comment.setTicket(ticketBdd.get());
                comment.setUtilisateur(userBdd.get());
                commentRepository.saveAndFlush(comment);

            } else {
                Optional<Admn> admnBdd = admnRepository.findById(userId);

                if(admnBdd.isPresent()){
                    comment.setCommentDate(LocalDateTime.now());
                    comment.setTicket(ticketBdd.get());
                    comment.setAdmin(admnBdd.get());
                    commentRepository.saveAndFlush(comment);
                }
            }
        } else {
            return ResponseEntity.badRequest().body("The specified ticket cannot be found.");
        }
        return ResponseEntity.ok("Comment added.");
    }



    @JsonView(CustomJsonView.CommentView.class)
    @DeleteMapping("/ticket/{ticketid}/comment/{commentid}/delete")
    public String deleteComment(@PathVariable Long ticketid,
                                @PathVariable Long commentid){

        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketid);
        Optional<Comment> commentBdd = commentRepository.findById(commentid);

        if(ticketBdd.isPresent() && commentBdd.isPresent()){
            commentRepository.deleteById(commentid);
            return "This comment has been successfully deleted.";
        } else {
            return "The comment you're trying to delete doesn't exist.";
        }
    }
}
