package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Admn;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.AdmnRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TicketController {

    private TicketRepository ticketRepository;
    private UtilisateurRepository utilisateurRepository;
    private AdmnRepository admnRepository;

    @Autowired
    TicketController (TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, AdmnRepository admnRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.ticketRepository = ticketRepository;
        this.admnRepository = admnRepository;
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
    @GetMapping("/ticket/{ticketid}")
    public ResponseEntity<Ticket> getSpecifiedTicket(@PathVariable Long ticketid){

       Optional<Ticket> ticketBdd = ticketRepository.findById(ticketid);
       if(ticketBdd.isPresent()){
           return ResponseEntity.ok(ticketBdd.get());
       } else {
           return ResponseEntity.noContent().build();
       }
    }



    @JsonView(CustomJsonView.TicketView.class)
    @PostMapping("/ticket/new")
    public ResponseEntity<String> newTicket(@RequestBody Ticket ticket){

        ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
        ticket.setEditableTicket(true);
        ticket.setTicketDateCreated(LocalDateTime.now());
        ticket.setTicketCategorization(Ticket.TicketCategorization.TOCATEGORIZE);
        ticket.setTicketType(Ticket.TicketType.REQUEST);
        ticket.setTicketStatus(Ticket.TicketStatus.OPEN);

        if(ticket.getUtilisateur() != null){
            Optional<Utilisateur> userBdd = utilisateurRepository.findById(ticket.getUtilisateur().getId());
            if(userBdd.isPresent()){
                ticket.setUtilisateur(userBdd.get());
            } else {
                return ResponseEntity.badRequest().body("The user trying to create this ticket doesn't exist.");
            }
        }

        if(ticket.getTicketPriority() != null){
            ticket.setTicketPriority(ticket.getTicketPriority());
        } else if (ticket.getTicketPriority() == null){
            ticket.setTicketPriority(Ticket.TicketPriority.LOW);
        } else {
            return ResponseEntity.badRequest().body("Invalid ticket priority.");
        }

        if(ticket.getAssignedAdmin() != null){
            Optional<Admn> admnBdd = admnRepository.findById(ticket.getAssignedAdmin().getId());
            if(admnBdd.isPresent()){
                ticket.setAssignedAdmin(admnBdd.get());
            } else {
                return ResponseEntity.badRequest().body("The specified admin doesn't exist.");
            }
        }

        ticketRepository.saveAndFlush(ticket);
        return ResponseEntity.ok("Ticket successfully created.");
    }



    @JsonView(CustomJsonView.TicketView.class)
    @PutMapping("/ticket/update/{ticketid}")
    public ResponseEntity<String> updateTicket (@PathVariable Long ticketid,
                                                @RequestBody Ticket ticket){

        Optional<Ticket> ticketBdd = ticketRepository.findById(ticketid);
        if(ticketBdd.isPresent()){

            ticketBdd.get().setTicketLastModified(LocalDateTime.now());

            if (ticket.getTicketTitle() != null) {
                ticketBdd.get().setTicketTitle(ticket.getTicketTitle());
            }

            if (ticket.getTicketType() != null) {
                ticketBdd.get().setTicketType(ticket.getTicketType());
            }

            if (ticket.getTicketPriority() != null) {
                ticketBdd.get().setTicketPriority(ticket.getTicketPriority());
            }

            if(ticket.getTicketStatus() != null && ticket.getTicketStatus() != Ticket.TicketStatus.CLOSED){
                ticketBdd.get().setTicketStatus(ticket.getTicketStatus());
            } else if (ticket.getTicketStatus() != null && ticket.getTicketStatus() == Ticket.TicketStatus.CLOSED) {
                ticketBdd.get().setTicketStatus(ticket.getTicketStatus());
                ticketBdd.get().setEditableTicket(false);
                ticketBdd.get().setTicketDateClosed(LocalDateTime.now());
            }

            if(ticket.getTicketCategorization() != null) {
                ticketBdd.get().setTicketCategorization(ticket.getTicketCategorization());
            }

            if (ticket.getTicketDescription() != null) {
                ticketBdd.get().setTicketDescription(ticket.getTicketDescription());
            }

            if(ticket.getUtilisateur() != null) {
                Optional<Utilisateur> userBdd = utilisateurRepository.findById(ticket.getUtilisateur().getId());
                if(userBdd.isPresent()){
                    ticketBdd.get().setUtilisateur(userBdd.get());
                }
            }

            if(ticket.getAssignedAdmin() != null){
                Optional<Admn> admnBdd = admnRepository.findById(ticket.getAssignedAdmin().getId());
                if(admnBdd.isPresent()){
                    ticketBdd.get().setAssignedAdmin(admnBdd.get());
                }
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
            String deletedTicket = "The following ticket : " + ticketBdd.get().getTicketTitle() + " has been deleted.";
            ticketRepository.deleteById(ticketId);
            return deletedTicket;
        } else {
            return "The specified ticket doesn't exist or an error occurred.";
        }
    }

}
