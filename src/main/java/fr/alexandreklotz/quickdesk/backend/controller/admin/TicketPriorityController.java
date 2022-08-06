package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketPriorityException;
import fr.alexandreklotz.quickdesk.backend.model.TicketPriority;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketPriorityController {

    private TicketPriorityService ticketPriorityService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketPriorityController(TicketPriorityService ticketPriorityService, DefaultValueService defaultValueService){
        this.ticketPriorityService = ticketPriorityService;
        this.defaultValueService = defaultValueService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/ticketpriority/all")
    public List<TicketPriority> getAllTicketPriorities(){
        return ticketPriorityService.getAllTicketPriorities();
    }

    //TODO : Rename/replace by GetById method.
    @GetMapping("/admin/ticketpriority/details")
    public TicketPriority getSpecifiedTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return ticketPriorityService.getSpecifiedTicketPriority(ticketPriority);
    }
    @GetMapping("/admin/ticketpriority/getdefault")
    public TicketPriority getDefaultTicketPriority() throws DefaultValueException {
        return defaultValueService.getDefaultPriorityValue();
    }

    @PostMapping("/admin/ticketpriority/new")
    public TicketPriority createTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException{
        return ticketPriorityService.createTicketPriority(ticketPriority);
    }

    @PutMapping("/admin/ticketpriority/update")
    public TicketPriority updateTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return ticketPriorityService.updateTicketPriority(ticketPriority);
    }

    @PutMapping("/admin/ticketpriority/setasdefault")
    public TicketPriority setDefaultTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return defaultValueService.setDefaultPriorityValue(ticketPriority);
    }

    @DeleteMapping("/admin/ticketpriority/id/{priorityId}/delete")
    public void deleteTicketPriorityById (@PathVariable UUID priorityId){
        ticketPriorityService.deleteTicketPriorityById(priorityId);
    }

}