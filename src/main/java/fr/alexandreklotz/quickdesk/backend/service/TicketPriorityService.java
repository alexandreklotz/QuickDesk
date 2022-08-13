package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.TicketPriorityException;
import fr.alexandreklotz.quickdesk.backend.model.TicketPriority;

import java.util.List;
import java.util.UUID;

public interface TicketPriorityService {

    public List<TicketPriority> getAllTicketPriorities();

    public TicketPriority getTicketPriorityById(UUID priorityId) throws TicketPriorityException;

    public TicketPriority getTicketPriorityByName(String priority) throws TicketPriorityException;

    public TicketPriority createTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException;

    public TicketPriority updateTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException;

    public void deleteTicketPriorityById(UUID priorityId);
}
