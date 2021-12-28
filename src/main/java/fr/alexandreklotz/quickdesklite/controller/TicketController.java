package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TicketController {

    private TicketRepository ticketRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    TicketController (TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.ticketRepository = ticketRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/ticket/all")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    @JsonView(CustomJsonView.TicketView.class)
    @PostMapping("user/ticket/new")
    public void newTicket(@RequestBody Ticket ticket){

        ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
        ticket.setTicketDateCreated(Date.from(Instant.now()));


        ticket.setTicketCategorization(Ticket.TicketCategorization.TOCATEGORIZE);

        if (ticket.getTicketType() == null) {
            ticket.setTicketType(Ticket.TicketType.REQUEST);
        } else {
            ticket.setTicketType(ticket.getTicketType());
        }

        ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
        ticket.setTicketPriority(Ticket.TicketPriority.LOW);

    }

    @JsonView(CustomJsonView.TicketView.class)
    @PutMapping("/ticket/update/{ticketId}")
    public ResponseEntity<String> updateTicket (@PathVariable Long ticketId, @RequestBody Ticket ticket){

        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketId);
        if(ticketBdd.isPresent()){

            if (ticket.getTicketTitle() != null) {
                ticketBdd.get().setTicketTitle(ticket.getTicketTitle());
            }

            if (ticket.getTicketType() != null) {
                ticketBdd.get().setTicketType(ticket.getTicketType());
            }

            if (ticket.getTicketPriority() != null) {
                ticketBdd.get().setTicketPriority(ticket.getTicketPriority());
            }

            if(ticket.getTicketStatus() != null){
                ticketBdd.get().setTicketStatus(ticket.getTicketStatus());
            }

            if(ticket.getTicketCategorization() != null) {
                ticketBdd.get().setTicketCategorization(ticket.getTicketCategorization());
            }

            if (ticket.getTicketDescription() != null) {
                ticketBdd.get().setTicketDescription(ticket.getTicketDescription());
            }

            if(ticket.getUtilisateur() != null) {
                ticketBdd.get().setUtilisateur(ticket.getUtilisateur());
            }

            ticketRepository.save(ticketBdd.get());

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.TicketView.class)
    @DeleteMapping("/ticket/delete/{ticketId}")
    public String deleteTicket (@PathVariable Long ticketId) {

        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketId);
        if(ticketBdd.isPresent()){
            String deletedTicket = ticketBdd.get().getTicketTitle() + " has been deleted.";
            ticketRepository.deleteById(ticketId);
            return deletedTicket;
        } else {
            return "The specified ticket doesn't exist or an error occurred.";
        }
    }

}
