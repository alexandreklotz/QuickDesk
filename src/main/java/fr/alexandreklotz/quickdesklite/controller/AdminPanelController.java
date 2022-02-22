package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class AdminPanelController {

    private UtilisateurRepository utilisateurRepository;
    private TicketRepository ticketRepository;
    private TeamRepository teamRepository;

    @Autowired
    AdminPanelController(UtilisateurRepository utilisateurRepository, TicketRepository ticketRepository, TeamRepository teamRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.ticketRepository = ticketRepository;
        this.teamRepository = teamRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    //TODO : In order to avoid the "Parameter value [cf642830-9414-11ec-817a-0a0027000005] did not match expected type" error, create a service package
    // with a UserTicketService or TicketService class which will iterate through all the tickets and retrieve those assigned/created by the specified UUID (could create a bean).

    @GetMapping("/admin/{userlogin}")
    public ModelAndView adminPanel(@PathVariable String userlogin){
        ModelAndView adminpanel = new ModelAndView("adminpanel");
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(userlogin);
        if(userBdd.isPresent()){
            Optional<Team> teamBdd = teamRepository.findById(userBdd.get().getTeam().getId());
            if(teamBdd.isPresent()){
                UUID adminid = userBdd.get().getId();
                Optional<List<Ticket>> ticketsOpened = ticketRepository.findTicketsOpened(adminid);
                Optional<List<Ticket>> ticketsAssigned = ticketRepository.findTicketsAssigned(adminid);
                String adminname = userBdd.get().toString();
                String adminteamname = teamBdd.get().toString();
                adminpanel.addObject("ticketsOpened", ticketsOpened);
                adminpanel.addObject("ticketsAssigned", ticketsAssigned);
                adminpanel.addObject("adminname", adminname);
                adminpanel.addObject("adminteam", adminteamname);
                return adminpanel;
            }
        }
        return adminpanel;
    }

}
