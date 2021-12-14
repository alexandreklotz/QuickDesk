package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.DeviceDao;
import fr.alexandreklotz.quickdesk.dao.TeamDao;
import fr.alexandreklotz.quickdesk.dao.TicketDao;
import fr.alexandreklotz.quickdesk.dao.UserDao;
import fr.alexandreklotz.quickdesk.model.Device;
import fr.alexandreklotz.quickdesk.model.Team;
import fr.alexandreklotz.quickdesk.model.Ticket;
import fr.alexandreklotz.quickdesk.model.User;
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
    private UserDao userDao;
    private DeviceDao deviceDao;
    private TeamDao teamDao;

    @Autowired
    TicketController (TicketDao ticketDao, UserDao userDao, DeviceDao deviceDao, TeamDao teamDao) {
        this.ticketDao = ticketDao;
        this.userDao = userDao;
        this.deviceDao = deviceDao;
        this.teamDao = teamDao;
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


        ticket.setTicketDateCreated(Date.from(Instant.now()));

        List<User> ticketUsers = ticket.getUser();
        for (User user : ticketUsers) {
            Optional<User> userBdd = userDao.findById(user.getUserId());
            if (userBdd.isPresent()) {
                ticket.setUser(ticket.getUser());
            }
        }

        List<Device> ticketDevices = ticket.getDevice();
        for (Device device : ticketDevices) {
            Optional<Device> deviceBdd = deviceDao.findById(device.getDeviceId());
            if(deviceBdd.isPresent()){
                ticket.setDevice(ticket.getDevice());
            }
        }

        /*TODO : See if this block is needed. The ManyToMany between ticket and team might be more of a trouble than something useful. The user's team will appear in the ticket details but do we need to assign a ticket to a whole team ?
            An IF needs to be implemented. If the ticket doesn't have a team assigned (which can be the case if the user isn't part of a service in its business or whatever */

        List<Team> ticketTeam = ticket.getTeam();
        for (Team team : ticketTeam) {
            Optional<Team> teamBdd = teamDao.findById(team.getTeamId());
            if(teamBdd.isPresent()){
                ticket.setTeam(ticket.getTeam());
            }
        }


        if (ticket.getTicketCategorization() == null) {
            ticket.setTicketCategorization(Ticket.TicketCategorization.TOCATEGORIZE);
        } else {
            ticket.setTicketCategorization(ticket.getTicketCategorization());
        }

        ticket.setTicketStatus(Ticket.TicketStatus.OPEN);
        ticket.setTicketPriority(Ticket.TicketPriority.LOW);
        ticket.setEditableTicket(true);

        /*ticket.setTicketTypes(ticket.getTicketTypes());
        ticket.setTicketPriority(ticket.getTicketPriority());*/

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
