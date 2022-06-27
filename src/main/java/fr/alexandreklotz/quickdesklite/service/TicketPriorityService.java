package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.TicketPriorityException;
import fr.alexandreklotz.quickdesklite.model.TicketPriority;

import java.util.List;

public interface TicketPriorityService {

    public List<TicketPriority> getAllTicketPriorities();

    public TicketPriority getSpecifiedTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException;

    public TicketPriority getTicketPriorityByName(String priority) throws TicketPriorityException;

    public TicketPriority createTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException;

    public TicketPriority updateTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException;

    public void deleteTicketPriority(TicketPriority ticketPriority);
}
