package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.TicketQueue;
import fr.alexandreklotz.quickdesklite.repository.TicketQueueRepository;
import fr.alexandreklotz.quickdesklite.service.TicketQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketQueueServiceImpl implements TicketQueueService {


    private TicketQueueRepository ticketQueueRepository;
    private DefaultValueServiceImpl defaultValueServiceImpl;

    @Autowired
    TicketQueueServiceImpl(TicketQueueRepository ticketQueueRepository, DefaultValueServiceImpl defaultValueServiceImpl){
        this.ticketQueueRepository = ticketQueueRepository;
        this.defaultValueServiceImpl = defaultValueServiceImpl;
    }


    //Methods


    @Override
    public List<TicketQueue> getAllTicketQueues() {
        return ticketQueueRepository.findAll();
    }

    @Override
    public TicketQueue getSpecifiedTicketQueue(TicketQueue ticketQueue) {
        Optional<TicketQueue> tQueue = ticketQueueRepository.findById(ticketQueue.getId());
        if(tQueue.isPresent()){
            return tQueue.get();
        } else {
            return null;
        }
    }

    @Override
    public TicketQueue createNewTicketQueue(TicketQueue ticketQueue) {
        if(ticketQueue.isDefault()){
            defaultValueServiceImpl.setDefaultTicketQueue(ticketQueue);
        }
        ticketQueueRepository.saveAndFlush(ticketQueue);
        return ticketQueue;
    }

    @Override
    public TicketQueue updateTicketQueue(TicketQueue ticketQueue) {
        Optional<TicketQueue> updatedQueue = ticketQueueRepository.findById(ticketQueue.getId());
        if(updatedQueue.isPresent()) {
            if (ticketQueue.isDefault()) {
                defaultValueServiceImpl.setDefaultTicketQueue(updatedQueue.get());
            }
            ticketQueueRepository.saveAndFlush(updatedQueue.get());
            return updatedQueue.get();
        } else {
            return null; //return an error
        }
    }

    @Override
    public void deleteTicketQueue(TicketQueue ticketQueue) {
        ticketQueueRepository.delete(ticketQueue);
    }

}
