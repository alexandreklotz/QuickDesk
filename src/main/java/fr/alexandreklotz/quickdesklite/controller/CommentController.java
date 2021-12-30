package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.CommentRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CommentController {

    private TicketRepository ticketRepository;
    private CommentRepository commentRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    CommentController(TicketRepository ticketRepository, CommentRepository commentRepository, UtilisateurRepository utilisateurRepository){
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.utilisateurRepository = utilisateurRepository;
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
    @PostMapping("/ticket/{ticketid}/comment/new")
    public void newComment (@PathVariable Long ticketid,
                            @PathVariable Long userid,
                            @RequestBody Comment comment){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(userid);
        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketid);

        if(ticketBdd.isPresent() && userBdd.isPresent()){
            comment.setCommentDate(Date.from(Instant.now()));
            comment.setTicket(ticketBdd.get());
            comment.setUtilisateur(userBdd.get());
            commentRepository.saveAndFlush(comment);
        }

    }

    //TODO : Implement the functionality to lock a comment if an admin/tech edits it. Or make the comment uneditable.
    @JsonView(CustomJsonView.CommentView.class)
    @PutMapping("/ticket/{ticketid}/comment/{commentid}/{userid}")
    public ResponseEntity<String> updateComment (@PathVariable Long ticketid,
                                                 @PathVariable Long commentid,
                                                 @PathVariable Long userid,
                                                 @RequestBody Comment comment){

        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketid);
        Optional<Utilisateur> userBdd = utilisateurRepository.findById(userid);
        Optional<Comment> commentBdd = commentRepository.findById(commentid);

        if (ticketBdd.isPresent() && commentBdd.isPresent() && userBdd.isPresent()) {
            if(comment.getCommentText() != null) {
                commentBdd.get().setCommentText(comment.getCommentText());
                commentBdd.get().setCommentLastModification(Date.from(Instant.now()));
                commentBdd.get().setUtilisateur(userBdd.get());
            }
            if(comment.getCommentText() == null) {
                return ResponseEntity.badRequest().body("A comment cannot be empty");
            }
        }
        else {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("Comment succesfully updated");
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
