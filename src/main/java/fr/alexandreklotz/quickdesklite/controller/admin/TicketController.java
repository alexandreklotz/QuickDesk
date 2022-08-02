package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketController {

    private TicketService ticketService;

    @Autowired
    TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    //////////////////
    // REST METHODS //
    //////////////////

    @GetMapping("/admin/assignedtickets")
    public List<Ticket> getAssignedTickets(HttpServletRequest request) throws UtilisateurException {
        Principal principal = request.getUserPrincipal();
        String adminLogin = principal.getName();
        return ticketService.getAssignedTickets(adminLogin);
    }

    @GetMapping("/admin/ticket/all")
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @GetMapping("/admin/ticket/{ticketId}")
    public Ticket getTicketByUUID(@PathVariable UUID ticketId) throws TicketException {
        return ticketService.getTicketById(ticketId);
    }

    @PostMapping("/admin/ticket/new")
    public Ticket createAdminTicket(@RequestBody Ticket ticket) throws UtilisateurException, DefaultValueException {
        return ticketService.createAdminTicket(ticket);
    }

    @PutMapping("/admin/ticket/update")
    public Ticket updateTicket(@RequestBody Ticket ticket) throws TicketException, DefaultValueException, UtilisateurException {
        return ticketService.updateTicket(ticket);
    }

    @PutMapping("/admin/ticket/close")
    public Ticket closeTicket(@RequestBody Ticket ticket) throws TicketException {
        return ticketService.closeTicket(ticket);
    }

    @DeleteMapping("/admin/ticket/delete")
    public void deleteTicket(@RequestBody Ticket ticket){
        ticketService.deleteTicket(ticket);
    }
}
