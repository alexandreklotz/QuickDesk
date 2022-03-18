package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin
public class TicketServiceController {

    private TicketRepository ticketRepository;
    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;

    @Autowired
    TicketServiceController(TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository) {
        this.ticketRepository = ticketRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @GetMapping("/user/userticketservice")
    public List<Ticket> getUserTickets(@PathVariable String userlogin) {

        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(userlogin);
        List<Ticket> userTickets = new ArrayList<>();

        if (userBdd.isPresent()) {
            List<Ticket> tickets = ticketRepository.findAll();
            for (Ticket ticket : tickets) {
                Utilisateur ticketUser = ticket.getUtilisateur();
                if (ticketUser.getId() == userBdd.get().getId()) {
                    userTickets.add(ticket);
                }
            }
        } return userTickets;
    }

    @GetMapping("/admin/adminticketservice")
    public List<Ticket> getAdminTickets(@PathVariable String userlogin) {

        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(userlogin);
        List<Ticket> assignedTickets = new ArrayList<>();

        if (userBdd.isPresent()) {
            List<Ticket> tickets = ticketRepository.findAll();
            for (Ticket ticket : tickets) {
                Optional<Utilisateur> ticketAdmin = utilisateurRepository.findById(ticket.getAssignedAdmin());
                if(ticketAdmin.isPresent() && ticketAdmin.get().getId() == userBdd.get().getId()){
                    assignedTickets.add(ticket);
                }
            }
        } return assignedTickets;
    }
}