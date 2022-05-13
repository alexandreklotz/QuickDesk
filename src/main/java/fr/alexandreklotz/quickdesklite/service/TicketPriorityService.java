package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.TicketPriority;

import java.util.List;

public interface TicketPriorityService {

    public List<TicketPriority> getAllTicketPriorities(TicketPriority ticketPriority);

    public TicketPriority getSpecifiedTicketPriority(TicketPriority ticketPriority);

    public TicketPriority createTicketPriority(TicketPriority ticketPriority);

    public TicketPriority updateTicketPriority(TicketPriority ticketPriority);

    public void deleteTicketPriority(TicketPriority ticketPriority);
}
