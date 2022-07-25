package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.TicketQueueException;
import fr.alexandreklotz.quickdesklite.model.TicketQueue;

import java.util.List;
import java.util.UUID;

public interface TicketQueueService {

    public List<TicketQueue> getAllTicketQueues();

    public TicketQueue getTicketQueueById(UUID queueId) throws TicketQueueException;

    public TicketQueue getTicketQueueByName(String queue) throws TicketQueueException;

    public TicketQueue createNewTicketQueue(TicketQueue ticketQueue) throws TicketQueueException;

    public TicketQueue updateTicketQueue(TicketQueue ticketQueue) throws TicketQueueException;

    public void deleteTicketQueue(TicketQueue ticketQueue);
}
