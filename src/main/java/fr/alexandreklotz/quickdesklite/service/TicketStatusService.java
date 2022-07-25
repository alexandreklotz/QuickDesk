package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketStatusException;
import fr.alexandreklotz.quickdesklite.model.TicketStatus;

import java.util.List;
import java.util.UUID;

public interface TicketStatusService {

    public List<TicketStatus> getAllTicketStatus();

    public TicketStatus getTicketStatusById(UUID statusId) throws TicketStatusException;

    public TicketStatus getTicketStatusByName(String status) throws TicketStatusException;

    public TicketStatus createTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public TicketStatus updateTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public void deleteTicketStatus(TicketStatus ticketStatus);
}
