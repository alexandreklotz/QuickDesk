package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    TicketService(TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository){
        this.ticketRepository = ticketRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //The two methods below are used to retrieve the tickets linked to the calling user.//
    //////////////////////////////////////////////////////////////////////////////////////

    public List<Ticket> getOpenedTickets(String login){
        UUID userId = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            userId = userBdd.get().getId();
        }

        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> openedTickets = new ArrayList<>();
        for(Ticket ticket : allTickets) {
            Utilisateur ticketUser = ticket.getUtilisateur();
            if (ticketUser.getId().equals(userId)) {
                openedTickets.add(ticket);
            }
        }
        return openedTickets;
    }


    public List<Ticket> getAssignedTickets(String login){
        UUID adminId = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            adminId = userBdd.get().getId();
        }
        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> assignedTickets = new ArrayList<>();
        for(Ticket ticket : allTickets){
            UUID assignedAdminId = ticket.getAssignedAdmin();
            if(assignedAdminId.equals(adminId)){
                assignedTickets.add(ticket);
            }
        }
        return assignedTickets;
    }
}
