package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.error.TicketException;
import fr.alexandreklotz.quickdesk.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.model.Ticket;
import fr.alexandreklotz.quickdesk.service.TicketService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/admin/assignedtickets")
    public List<Ticket> getAssignedTickets(HttpServletRequest request) throws UtilisateurException {
        Principal principal = request.getUserPrincipal();
        String adminLogin = principal.getName();
        return ticketService.getAssignedTickets(adminLogin);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/admin/ticket/all")
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/admin/ticket/id/{ticketId}")
    public Ticket getTicketByUUID(@PathVariable UUID ticketId) throws TicketException {
        return ticketService.getTicketById(ticketId);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @PostMapping("/admin/ticket/create")
    public Ticket createAdminTicket(@RequestBody Ticket ticket) throws UtilisateurException, DefaultValueException {
        return ticketService.createAdminTicket(ticket);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @PutMapping("/admin/ticket/update")
    public Ticket updateTicket(@RequestBody Ticket ticket) throws TicketException, DefaultValueException, UtilisateurException {
        return ticketService.updateTicket(ticket);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @PutMapping("/admin/ticket/close")
    public Ticket closeTicket(@RequestBody Ticket ticket) throws TicketException {
        return ticketService.closeTicket(ticket);
    }

    @JsonView(CustomJsonView.TicketView.class)
    @DeleteMapping("/admin/ticket/id/{ticketId}/delete")
    public void deleteTicketById(@PathVariable UUID ticketId){
        ticketService.deleteTicketById(ticketId);
    }
}
