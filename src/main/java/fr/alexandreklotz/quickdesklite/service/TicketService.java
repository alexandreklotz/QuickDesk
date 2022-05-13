package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    public List<Ticket> getOpenedTickets(String login);

    public List<Ticket> getAssignedTickets(String login);

    public List<Ticket> getAllTickets();

    public Ticket getTicketById(UUID ticketid);

    public Ticket getSpecifiedTicket(Long ticketnbr);

    public Ticket createUserTicket(Ticket ticket);

    public Ticket createAdminTicket(Ticket ticket);

    public Ticket updateTicket(Ticket ticket);

    public void closeTicket(Ticket ticket);

    public void deleteTicket(UUID ticketid);

}
