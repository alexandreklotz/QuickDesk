package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.TicketQueueException;
import fr.alexandreklotz.quickdesklite.model.TicketQueue;
import fr.alexandreklotz.quickdesklite.repository.TicketQueueRepository;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public TicketQueue getSpecifiedTicketQueueById(TicketQueue ticketQueue) throws TicketQueueException {
        return ticketQueueRepository.findById(ticketQueue.getId()).orElseThrow(()
        -> new TicketQueueException(ticketQueue.getId() + " doesn't match any existing ticket queue."));
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
            throw new TicketQueueException("A ticket queue already uses this name. Please specify another name or update the existing entity.");
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
        if(!updatedQueue.isPresent()) {
            throw new TicketQueueException("The ticket queue you're trying to update doesn't exist.");
        }
        Optional<TicketQueue> existingQueue = ticketQueueRepository.findTicketQueueByName(ticketQueue.getName());
        if(existingQueue.isPresent()){
            throw new TicketQueueException("A ticket queue already uses this name. Please specify another name or update the existing entity.");
        }
        if(ticketQueue.isDefault()){
            defaultValueService.setDefaultTicketQueue(updatedQueue.get());
        }

        if(ticketQueue.getName() != null){
            updatedQueue.get().setName(ticketQueue.getName());
        }

        return ticketQueueRepository.saveAndFlush(updatedQueue.get());
    }

    @Override
    public void deleteTicketQueue(TicketQueue ticketQueue) {
        ticketQueueRepository.delete(ticketQueue);
    }

}
