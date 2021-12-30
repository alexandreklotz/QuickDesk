package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Comment;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.repository.CommentRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CommentController {

    private TicketRepository ticketRepository;
    private CommentRepository commentRepository;

    @Autowired
    CommentController(TicketRepository ticketRepository, CommentRepository commentRepository){
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.CommentView.class)
    @GetMapping("/comments/all")
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentRepository.findAll());
    }

}
