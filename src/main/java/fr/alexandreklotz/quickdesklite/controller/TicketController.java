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

        ticketRepository.saveAndFlush(ticket);

    }
}
