package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.backend.model.TicketQueue;
import fr.alexandreklotz.quickdesk.backend.repository.TicketQueueRepository;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketQueueServiceImpl implements TicketQueueService {


    private TicketQueueRepository ticketQueueRepository;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketQueueServiceImpl(TicketQueueRepository ticketQueueRepository, DefaultValueService defaultValueService){
        this.ticketQueueRepository = ticketQueueRepository;
        this.defaultValueService = defaultValueService;
    }


    //Methods


    @Override
    public List<TicketQueue> getAllTicketQueues() {
        return ticketQueueRepository.findAll();
    }

    @Override
    public TicketQueue getTicketQueueById(UUID queueId) throws TicketQueueException {
        return ticketQueueRepository.findById(queueId).orElseThrow(()
        -> new TicketQueueException(queueId + " doesn't match any existing ticket queue."));
    }

    @Override
    public TicketQueue getTicketQueueByName(String queue) throws TicketQueueException {
        return ticketQueueRepository.findTicketQueueByName(queue).orElseThrow(()
        -> new TicketQueueException(queue + " doesn't match any existing ticket queue."));
    }

    @Override
    public TicketQueue createNewTicketQueue(TicketQueue ticketQueue) throws TicketQueueException{
        Optional<TicketQueue> existingQueue = ticketQueueRepository.findTicketQueueByName(ticketQueue.getName());
        if(existingQueue.isPresent()){
            throw new TicketQueueException("ERROR : A ticket queue already uses this name. Please specify another name or update the existing entity.");
        }

        if(ticketQueue.isDefault()){
            defaultValueService.setDefaultTicketQueue(ticketQueue);
        }

        ticketQueueRepository.saveAndFlush(ticketQueue);
        return ticketQueue;
    }

    @Override
    public TicketQueue updateTicketQueue(TicketQueue ticketQueue) throws TicketQueueException {
        Optional<TicketQueue> updatedQueue = ticketQueueRepository.findById(ticketQueue.getId());
        if(updatedQueue.isEmpty()) {
            throw new TicketQueueException("ERROR : The ticket queue " + ticketQueue.getName() + " you're trying to update doesn't exist.");
        }
        Optional<TicketQueue> existingQueue = ticketQueueRepository.findTicketQueueByName(ticketQueue.getName());
        if(existingQueue.isPresent()){
            throw new TicketQueueException("ERROR : A ticket queue already uses this name. Please specify another name or update the existing entity.");
        }
        if(ticketQueue.isDefault()){
            defaultValueService.setDefaultTicketQueue(ticketQueue);
        }

        return ticketQueueRepository.saveAndFlush(ticketQueue);
    }

    @Override
    public void deleteTicketQueueById(UUID queueId) {
        ticketQueueRepository.deleteById(queueId);
    }

}
