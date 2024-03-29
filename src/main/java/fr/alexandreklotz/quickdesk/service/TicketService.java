package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.*;
import fr.alexandreklotz.quickdesk.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    public Long getLatestTicketNumber();

    public List<Ticket> getOpenedTickets(String login) throws UtilisateurException;

    public List<Ticket> getAssignedTickets(String login) throws UtilisateurException;

    public List<Ticket> getAllTickets();

    public Ticket getTicketById(UUID ticketid) throws TicketException;

    public Ticket getTicketByNumber(Long ticketNbr, String login) throws TicketException, UtilisateurException;

    public List<Ticket> getClosedTickets(boolean closed);

    public Ticket createUserTicket(Ticket ticket) throws DefaultValueException;

    public Ticket createAdminTicket(Ticket ticket) throws UtilisateurException, DefaultValueException;

    public Ticket updateTicket(Ticket ticket) throws TicketException, DefaultValueException, UtilisateurException;

    public Ticket closeTicket(Ticket ticket) throws TicketException;

    public void deleteTicketById(UUID ticketId);

}
