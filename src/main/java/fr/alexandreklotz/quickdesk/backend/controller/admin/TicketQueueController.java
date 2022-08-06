package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.backend.model.TicketQueue;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketQueueController {

    private TicketQueueService ticketQueueService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketQueueController(TicketQueueService ticketQueueService, DefaultValueService defaultValueService){
        this.ticketQueueService = ticketQueueService;
        this.defaultValueService = defaultValueService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/ticketqueue/all")
    public List<TicketQueue> getAllTicketQueues(){
        return ticketQueueService.getAllTicketQueues();
    }

    @GetMapping("/admin/ticketqueue/id/{queueId}")
    public TicketQueue getTicketQueueById(@PathVariable UUID queueId) throws TicketQueueException {
        return  ticketQueueService.getTicketQueueById(queueId);
    }

    @GetMapping("/admin/ticketqueue/name/{queueName}")
    public TicketQueue getTicketQueueByName(@PathVariable String queueName) throws TicketQueueException{
        return ticketQueueService.getTicketQueueByName(queueName);
    }

    @GetMapping("/admin/ticketqueue/getdefault")
    public TicketQueue getDefaultTicketQueue() throws DefaultValueException {
        return defaultValueService.getDefaultTicketQueue();
    }

    @PostMapping("/admin/ticketqueue/new")
    public TicketQueue createNewTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException{
        return ticketQueueService.createNewTicketQueue(ticketQueue);
    }

    @PutMapping("/admin/ticketqueue/update")
    public TicketQueue updateTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException {
        return ticketQueueService.updateTicketQueue(ticketQueue);
    }

    @PutMapping("/admin/ticketqueue/setasdefault")
    public TicketQueue setDefaultTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException {
        return defaultValueService.setDefaultTicketQueue(ticketQueue);
    }

    @DeleteMapping("/admin/ticketqueue/id/{queueId}/delete")
    public void deleteTicketQueueById(@PathVariable UUID queueId) {
        ticketQueueService.deleteTicketQueueById(queueId);
    }
}
