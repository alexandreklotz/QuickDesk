package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.TicketPriorityException;
import fr.alexandreklotz.quickdesklite.model.TicketPriority;
import fr.alexandreklotz.quickdesklite.service.TicketPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TicketPriorityController {

    private TicketPriorityService ticketPriorityService;

    @Autowired
    TicketPriorityController(TicketPriorityService ticketPriorityService){
        this.ticketPriorityService = ticketPriorityService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/ticketpriority/all")
    public List<TicketPriority> getAllTicketPriorities(){
        return ticketPriorityService.getAllTicketPriorities();
    }

    @GetMapping("/admin/ticketpriority/details")
    public TicketPriority getSpecifiedTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return ticketPriorityService.getSpecifiedTicketPriority(ticketPriority);
    }

    @PostMapping("/admin/ticketpriority/new")
    public TicketPriority createTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException{
        return ticketPriorityService.createTicketPriority(ticketPriority);
    }

    @PostMapping("/admin/ticketpriority/update")
    public TicketPriority updateTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return ticketPriorityService.updateTicketPriority(ticketPriority);
    }

    @DeleteMapping("/admin/ticketpriority/delete")
    public void deleteTicketPriority (@RequestBody TicketPriority ticketPriority){
        ticketPriorityService.deleteTicketPriority(ticketPriority);
    }

}
