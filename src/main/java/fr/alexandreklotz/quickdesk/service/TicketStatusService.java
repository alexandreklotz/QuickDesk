package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.TicketStatusException;
import fr.alexandreklotz.quickdesk.model.TicketStatus;

import java.util.List;
import java.util.UUID;

public interface TicketStatusService {

    public List<TicketStatus> getAllTicketStatus();

    public TicketStatus getTicketStatusById(UUID statusId) throws TicketStatusException;

    public TicketStatus getTicketStatusByName(String status) throws TicketStatusException;

    public TicketStatus createTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public TicketStatus updateTicketStatus(TicketStatus ticketStatus) throws TicketStatusException;

    public void deleteTicketStatusById(UUID statusId);
}
