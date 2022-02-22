package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin
public class UserPanelController {

    private UtilisateurRepository utilisateurRepository;
    private TicketRepository ticketRepository;
    private TeamRepository teamRepository;

    @Autowired
    UserPanelController(UtilisateurRepository utilisateurRepository, TicketRepository ticketRepository, TeamRepository teamRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.ticketRepository = ticketRepository;
        this.teamRepository = teamRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @GetMapping("/homepage/{userlogin}")
    public ModelAndView userPanel(@PathVariable String userlogin){
        ModelAndView homepage = new ModelAndView("homepage");
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(userlogin);
        if(userBdd.isPresent()){
            Optional<Team> teamBdd = teamRepository.findById(userBdd.get().getTeam().getId());
            if(teamBdd.isPresent()) {
                UUID userid = userBdd.get().getId();
                //Set<Ticket> tickets = userBdd.get().getTickets();
                Optional<List<Ticket>> ticketsOpened = ticketRepository.findTicketsOpened(userid);
                String username = userBdd.get().toString();
                String userteamname = teamBdd.get().toString();
                homepage.addObject("usertickets", ticketsOpened);
                homepage.addObject("username", username);
                homepage.addObject("userteam", userteamname);
                return homepage;
            }
        }
        return homepage;
    }
}
