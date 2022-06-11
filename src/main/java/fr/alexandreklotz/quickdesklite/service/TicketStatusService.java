package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.TicketStatus;

import java.util.List;

public interface TicketStatusService {

    public List<TicketStatus> getAllTicketStatus();

    public TicketStatus getSpecifiedTicketStatus(TicketStatus ticketStatus);

    public TicketStatus getTicketStatusByName(String status);

    public TicketStatus createTicketStatus(TicketStatus ticketStatus);

    public TicketStatus updateTicketStatus(TicketStatus ticketStatus);

    public void deleteTicketStatus(TicketStatus ticketStatus);
}
