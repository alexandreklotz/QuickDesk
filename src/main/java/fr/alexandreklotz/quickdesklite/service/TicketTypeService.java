package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.TicketType;

import java.util.List;

public interface TicketTypeService {

    public List<TicketType> getAllTicketTypes();

    public TicketType getSpecifiedTicketType(TicketType ticketType);

    public TicketType getTicketTypeByName(String type);

    public TicketType createTicketType(TicketType ticketType);

    public TicketType updateTicketType(TicketType ticketType);

    public void deleteTicketType(TicketType ticketType);
}
