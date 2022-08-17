package fr.alexandreklotz.quickdesk.backend.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.backend.model.TicketQueue;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketQueueService;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TicketQueueView.class)
    @GetMapping("/admin/ticketqueue/all")
    public List<TicketQueue> getAllTicketQueues(){
        return ticketQueueService.getAllTicketQueues();
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @GetMapping("/admin/ticketqueue/id/{queueId}")
    public TicketQueue getTicketQueueById(@PathVariable UUID queueId) throws TicketQueueException {
        return  ticketQueueService.getTicketQueueById(queueId);
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @GetMapping("/admin/ticketqueue/name/{queueName}")
    public TicketQueue getTicketQueueByName(@PathVariable String queueName) throws TicketQueueException{
        return ticketQueueService.getTicketQueueByName(queueName);
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @GetMapping("/admin/ticketqueue/getdefault")
    public TicketQueue getDefaultTicketQueue() throws DefaultValueException {
        return defaultValueService.getDefaultTicketQueue();
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @PostMapping("/admin/ticketqueue/create")
    public TicketQueue createNewTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException{
        return ticketQueueService.createNewTicketQueue(ticketQueue);
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @PutMapping("/admin/ticketqueue/update")
    public TicketQueue updateTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException {
        return ticketQueueService.updateTicketQueue(ticketQueue);
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @PutMapping("/admin/ticketqueue/setasdefault")
    public TicketQueue setDefaultTicketQueue(@RequestBody TicketQueue ticketQueue) throws TicketQueueException {
        return defaultValueService.setDefaultTicketQueue(ticketQueue);
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @DeleteMapping("/admin/ticketqueue/id/{queueId}/delete")
    public void deleteTicketQueueById(@PathVariable UUID queueId) {
        ticketQueueService.deleteTicketQueueById(queueId);
    }
}
