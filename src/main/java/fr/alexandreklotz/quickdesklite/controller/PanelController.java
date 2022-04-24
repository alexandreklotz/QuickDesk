package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.MyPanelService;
import fr.alexandreklotz.quickdesklite.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin
public class PanelController {

    private MyPanelService myPanelService;
    private TicketService ticketService;

    @Autowired
    PanelController(MyPanelService myPanelService,TicketService ticketService){
        this.myPanelService = myPanelService;
        this.ticketService = ticketService;
    }

    /* This request uses the myPanelService to verify that the user trying to access the specified panel is his. If not, he's redirected. If yes, we generate
    a modelandview. Mainly used to prevent unauthorized access. There's probably a better way to do that such as something linked to the HttpSession
     or to the cookie, maybe JWT aswell... I'll improve it if i find something better which i'm sure i will */
    @GetMapping("/mypanel/{login}")
    public ModelAndView userPanel(@PathVariable String login, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView panel = new ModelAndView("adminpanel");
        Principal principal = request.getUserPrincipal();
        String userRealLogin = principal.getName();
        boolean panelCheck = myPanelService.userPanelVerification(login, userRealLogin);

        if (panelCheck){
            boolean isAdmin = myPanelService.isUserAdmin(userRealLogin);
            if(isAdmin){
                panel.addObject("username", myPanelService.getUserFullName(userRealLogin));
                panel.addObject("userdevice", myPanelService.getUserDevice(userRealLogin));
                panel.addObject("openedTickets", ticketService.getOpenedTickets(userRealLogin));
                panel.addObject("assignedTickets", ticketService.getAssignedTickets(userRealLogin));
            } else {
                panel.addObject("username", myPanelService.getUserFullName(userRealLogin));
                panel.addObject("userdevice", myPanelService.getUserDevice(userRealLogin));
                panel.addObject("openedTickets", ticketService.getOpenedTickets(userRealLogin));
            }

        } else {
            response.sendRedirect("/mypanel/" + userRealLogin);
        }
        return panel;
    }

}
