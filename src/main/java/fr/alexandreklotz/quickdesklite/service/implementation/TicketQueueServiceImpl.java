package fr.alexandreklotz.quickdesklite.service.implementation;

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
    public TicketQueue getSpecifiedTicketQueueById(TicketQueue ticketQueue) {
        Optional<TicketQueue> tQueue = ticketQueueRepository.findById(ticketQueue.getId());
        if(tQueue.isPresent()){
            return tQueue.get();
        } else {
            return null;
        }
    }

    @Override
    public TicketQueue getTicketQueueByName(String queue) {
        Optional<TicketQueue> searchedQueue = ticketQueueRepository.findTicketQueueByName(queue);
        if(!searchedQueue.isPresent()){
            return null; //doesn't exist
        }
        return searchedQueue.get();
    }

    @Override
    public TicketQueue createNewTicketQueue(TicketQueue ticketQueue) {
        Optional<TicketQueue> existingQueue = ticketQueueRepository.findTicketQueueByName(ticketQueue.getName());
        if(existingQueue.isPresent()){
            return null; //a queue with this name already exists
        }
        if(ticketQueue.isDefault()){
            defaultValueService.setDefaultTicketQueue(ticketQueue);
        }
        ticketQueueRepository.saveAndFlush(ticketQueue);
        return ticketQueue;
    }

    @Override
    public TicketQueue updateTicketQueue(TicketQueue ticketQueue) {
        Optional<TicketQueue> updatedQueue = ticketQueueRepository.findById(ticketQueue.getId());
        if(!updatedQueue.isPresent()) {
            return null; //the queue you're trying to update doesn't exist
        }
        Optional<TicketQueue> existingQueue = ticketQueueRepository.findTicketQueueByName(ticketQueue.getName());
        if(existingQueue.isPresent()){
            return null; //a queue with this name already exists
        }
        if(ticketQueue.isDefault()){
            defaultValueService.setDefaultTicketQueue(updatedQueue.get());
        }

        updatedQueue.get().setName(ticketQueue.getName());
        return ticketQueueRepository.saveAndFlush(updatedQueue.get());
    }

    @Override
    public void deleteTicketQueue(TicketQueue ticketQueue) {
        ticketQueueRepository.delete(ticketQueue);
    }

}
