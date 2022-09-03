package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.TicketTypeException;
import fr.alexandreklotz.quickdesk.model.TicketType;

import java.util.List;
import java.util.UUID;

public interface TicketTypeService {

    public List<TicketType> getAllTicketTypes();

    public TicketType getTicketTypeById (UUID typeId) throws TicketTypeException;

    public TicketType getTicketTypeByName(String type) throws TicketTypeException;

    public TicketType createTicketType(TicketType ticketType) throws TicketTypeException;

    public TicketType updateTicketType(TicketType ticketType) throws TicketTypeException;

    public void deleteTicketTypeById(UUID typeId);
}
