package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketStatusException;
import fr.alexandreklotz.quickdesklite.model.TicketStatus;

import java.util.List;

public interface TicketStatusService {

    public List<TicketStatus> getAllTicketStatus();

    public TicketStatus getSpecifiedTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public TicketStatus getTicketStatusByName(String status) throws TicketStatusException;

    public TicketStatus createTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public TicketStatus updateTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public void deleteTicketStatus(TicketStatus ticketStatus);
}
