package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.TicketQueue;

import java.util.List;

public interface TicketQueueService {

    public List<TicketQueue> getAllTicketQueues();

    public TicketQueue getSpecifiedTicketQueue(TicketQueue ticketQueue);

    public TicketQueue createNewTicketQueue(TicketQueue ticketQueue);

    public TicketQueue updateTicketQueue(TicketQueue ticketQueue);

    public void deleteTicketQueue(TicketQueue ticketQueue);
}
