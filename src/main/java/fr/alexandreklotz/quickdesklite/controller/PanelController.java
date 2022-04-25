package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.service.TicketService;
import fr.alexandreklotz.quickdesklite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin
public class PanelController {

    private TicketService ticketService;
    private UtilisateurService utilisateurService;

    @Autowired
    PanelController(TicketService ticketService, UtilisateurService utilisateurService){
        this.ticketService = ticketService;
        this.utilisateurService = utilisateurService;
    }

    //TODO : should the user be redirected if he would get an error instead of getting his panel ?
    @GetMapping("/mypanel")
    public ModelAndView userPanel (HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView panel = new ModelAndView("adminpanel");
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        boolean existingUser = utilisateurService.isUserExisting(userLogin);

        if(existingUser) {
            boolean isAdmin = utilisateurService.isUserAdmin(userLogin);
            if (isAdmin) {
                panel.addObject("username", utilisateurService.getUserFullName(userLogin));
                panel.addObject("userdevice", utilisateurService.getUserDevice(userLogin));
                panel.addObject("openedTickets", ticketService.getOpenedTickets(userLogin));
                panel.addObject("assignedTickets", ticketService.getAssignedTickets(userLogin));
            } else {
                panel.addObject("username", utilisateurService.getUserFullName(userLogin));
                panel.addObject("userdevice", utilisateurService.getUserDevice(userLogin));
                panel.addObject("openedTickets", ticketService.getOpenedTickets(userLogin));
            }
        }

        return panel;
    }
}
