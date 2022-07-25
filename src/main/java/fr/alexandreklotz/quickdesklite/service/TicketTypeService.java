package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.TicketTypeException;
import fr.alexandreklotz.quickdesklite.model.TicketType;

import java.util.List;
import java.util.UUID;

public interface TicketTypeService {

    public List<TicketType> getAllTicketTypes();

    public TicketType getTicketTypeById (UUID typeId) throws TicketTypeException;

    public TicketType getTicketTypeByName(String type) throws TicketTypeException;

    public TicketType createTicketType(TicketType ticketType) throws TicketTypeException;

    public TicketType updateTicketType(TicketType ticketType) throws TicketTypeException;

    public void deleteTicketType(TicketType ticketType);
}
