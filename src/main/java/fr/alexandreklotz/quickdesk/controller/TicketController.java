package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.TicketDao;
import fr.alexandreklotz.quickdesk.model.Ticket;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TicketController {

    private TicketDao ticketDao;

    @Autowired
    TicketController (TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TicketView.class)
    @GetMapping("/ticket/all")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return ResponseEntity.ok(ticketDao.findAll());
    }

    @JsonView(CustomJsonView.TicketView.class)
    @PostMapping("/ticket/new")
    public void newTicket(@RequestBody Ticket ticket){

        ticket.setTicketTitle(ticket.getTicketTitle());
        ticket.setTicketDescription(ticket.getTicketDescription());
        ticket.setTicketDateCreated(Date.from(Instant.now()));
        //ticket.setUsers(ticket.getUsers());
        //ticket.setDevices(ticket.getDevices());
        //ticket.setTkteams(ticket.getTkteams());

        ticket.setTicketCategorization(ticket.getTicketCategorization());
        ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
        ticket.setTicketTypes(ticket.getTicketTypes());
        ticket.setTicketPriority(ticket.getTicketPriority());

        ticketDao.saveAndFlush(ticket);
    }

    /*@JsonView(CustomJsonView.TicketView.class)
    @PatchMapping("/ticket/update/{ticketId}")*/


    //TODO : Implement a patch request that allows the user to modify a few fields once the ticket is created and that allows the admin to modify/close the ticket (if an admin closes the ticket it makes it uneditable but it doesn't delete it)
    @JsonView(CustomJsonView.TicketView.class)
    @DeleteMapping("/ticket/delete/{ticketId}")
    public String deleteTicket (@PathVariable int ticketId) {

        Optional<Ticket> ticketBdd = ticketDao.findById(ticketId);

        if (ticketBdd.isPresent()) {
            String ticketInfo = ticketBdd.get().getTicketId() + " - " + ticketBdd.get().getTicketTitle();
            ticketDao.deleteById(ticketId);
            return "The ticket " + ticketInfo + " has been deleted.";
        }
        return "The specified ticket doesn't exist.";
    }

}
