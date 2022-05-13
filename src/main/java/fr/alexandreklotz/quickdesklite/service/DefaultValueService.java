package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.*;

public interface DefaultValueService {

    public TicketStatus getDefaultStatusValue();

    public TicketStatus setDefaultStatusValue(TicketStatus ticketStatus);

    public TicketCategory getDefaultCategoryValue();

    public TicketCategory setDefaultCategoryValue(TicketCategory ticketCategory);

    public TicketType getDefaultTypeValue();

    public TicketType setDefaultTypeValue(TicketType ticketType);

    public TicketPriority getDefaultPriorityValue();

    public TicketPriority setDefaultPriorityValue(TicketPriority ticketPriority);

    public TicketQueue getDefaultTicketQueue();

    public TicketQueue setDefaultTicketQueue(TicketQueue ticketQueue);
}
