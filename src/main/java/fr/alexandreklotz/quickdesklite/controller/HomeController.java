package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
public class HomeController {

    private UtilisateurRepository utilisateurRepository;
    private TicketRepository ticketRepository;
    private RolesRepository rolesRepository;

    @Autowired
    HomeController (UtilisateurRepository utilisateurRepository, TicketRepository ticketRepository, RolesRepository rolesRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.ticketRepository = ticketRepository;
        this.rolesRepository = rolesRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    /*
    @RequestMapping("/testpanel")
    @RolesAllowed("ADMIN")
    public ModelAndView homepage(){
        ModelAndView modelAndView = new ModelAndView("testpanel");
        List<Ticket> tickets = ticketRepository.findAll();
        List<Utilisateur> users = utilisateurRepository.findAll();

        modelAndView.addObject("tickets", tickets);
        modelAndView.addObject("users", users);

        return modelAndView;
    }*/
}
