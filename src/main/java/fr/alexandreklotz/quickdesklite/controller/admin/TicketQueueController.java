package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.TicketQueueException;
import fr.alexandreklotz.quickdesklite.model.TicketQueue;
import fr.alexandreklotz.quickdesklite.service.TicketQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketQueueController {

    private TicketQueueService ticketQueueService;

    @Autowired
    TicketQueueController(TicketQueueService ticketQueueService){
        this.ticketQueueService = ticketQueueService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/ticketqueue/all")
    public List<TicketQueue> getAllTicketQueues(){
        return ticketQueueService.getAllTicketQueues();
    }

    @GetMapping("/admin/ticketqueue/{queueId}")
    public TicketQueue getTicketQueueById(@PathVariable UUID queueId) throws TicketQueueException {
        return  ticketQueueService.getTicketQueueById(queueId);
    }

    @GetMapping("/admin/ticketqueue/{queueName}")
    public TicketQueue getTicketQueueByName(@PathVariable String queueName) throws TicketQueueException{
        return ticketQueueService.getTicketQueueByName(queueName);
    }

    @PostMapping("/admin/ticketqueue/new")
    public TicketQueue createNewTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException{
        return ticketQueueService.createNewTicketQueue(ticketQueue);
    }

    @PostMapping("/admin/ticketqueue/update")
    public TicketQueue updateTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException {
        return ticketQueueService.updateTicketQueue(ticketQueue);
    }

    @DeleteMapping("/admin/ticketqueue/delete")
    public void deleteTicketQueue(@RequestBody TicketQueue ticketQueue) {
        ticketQueueService.deleteTicketQueue(ticketQueue);
    }
}
