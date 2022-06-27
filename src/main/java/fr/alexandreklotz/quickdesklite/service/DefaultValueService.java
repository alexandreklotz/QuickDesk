package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.*;
import fr.alexandreklotz.quickdesklite.model.*;

public interface DefaultValueService {

    public TicketStatus getDefaultStatusValue() throws DefaultValueException;

    public TicketStatus setDefaultStatusValue(TicketStatus ticketStatus) throws TicketStatusException;

    public TicketCategory getDefaultCategoryValue() throws DefaultValueException;

    public TicketCategory setDefaultCategoryValue(TicketCategory ticketCategory) throws TicketCategoryException;

    public TicketType getDefaultTypeValue() throws DefaultValueException;

    public TicketType setDefaultTypeValue(TicketType ticketType) throws TicketTypeException;

    public TicketPriority getDefaultPriorityValue() throws DefaultValueException;

    public TicketPriority setDefaultPriorityValue(TicketPriority ticketPriority) throws TicketPriorityException;

    public TicketQueue getDefaultTicketQueue() throws DefaultValueException;

    public TicketQueue setDefaultTicketQueue(TicketQueue ticketQueue) throws TicketQueueException;
}
