package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
public class TicketController {

    private TicketRepository ticketRepository;
    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;

    @Autowired
    TicketController (TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.ticketRepository = ticketRepository;
        this.rolesRepository = rolesRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/admin/ticket/all")
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
                    return ResponseEntity.badRequest().body("One of the specified users doesn't exist.");
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
            Optional<Utilisateur> adminBdd = utilisateurRepository.findById(ticket.getAssignedAdmin());
            if (adminBdd.isPresent()) {
                Optional<Roles> roleAdmin = rolesRepository.findById(adminBdd.get().getRole().getId());
                if(Objects.equals(roleAdmin.get().getRoleName(), "ADMIN")) {
                    ticket.setAssignedAdmin(adminBdd.get().getId());
                } else {
                    return ResponseEntity.badRequest().body("The user you're trying to assign this ticket to isn't an admin.");
                }
            } else {
                return ResponseEntity.badRequest().body("The admin you're trying to assign to this ticket to doesn't exist.");
            }
        }

        ticketRepository.saveAndFlush(ticket);
        return ResponseEntity.ok("Ticket successfully created.");
    }



    @JsonView(CustomJsonView.TicketView.class)
    @PutMapping("/admin/ticket/update/{ticketid}")
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

            if(ticket.getAssignedAdmin() != null) {
                Optional<Utilisateur> adminBdd = utilisateurRepository.findById(ticket.getAssignedAdmin());
                if (adminBdd.isPresent()) {
                    Optional<Roles> roleAdmin = rolesRepository.findById(adminBdd.get().getRole().getId());
                    if(roleAdmin.get().getRoleName() == "ADMIN") {
                        ticket.setAssignedAdmin(adminBdd.get().getId());
                    } else {
                        return ResponseEntity.badRequest().body("The user you're trying to assign this ticket to isn't an admin.");
                    }
                } else {
                    return ResponseEntity.badRequest().body("The admin you're trying to assign this ticket to doesn't exist.");
                }
            } else if (ticket.getAssignedAdmin() == null) {
                ticketBdd.get().setAssignedAdmin(null);
            }

            ticketRepository.save(ticketBdd.get());

            return ResponseEntity.ok(ticketBdd.get().getTicketTitle() + " has been updated.");
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @JsonView(CustomJsonView.TicketView.class)
    @DeleteMapping("/admin/ticket/delete/{ticketId}")
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
