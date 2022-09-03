package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.error.TicketPriorityException;
import fr.alexandreklotz.quickdesk.model.TicketPriority;
import fr.alexandreklotz.quickdesk.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.service.TicketPriorityService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @GetMapping("/admin/ticketpriority/all")
    public List<TicketPriority> getAllTicketPriorities(){
        return ticketPriorityService.getAllTicketPriorities();
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @GetMapping("/admin/ticketpriority/id/{priorityId}")
    public TicketPriority getTicketPriorityById(@PathVariable UUID priorityId) throws TicketPriorityException {
        return ticketPriorityService.getTicketPriorityById(priorityId);
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @GetMapping("/admin/ticketpriority/name/{priorityName}")
    public TicketPriority getTicketPriorityByName(@PathVariable String priorityName) throws TicketPriorityException {
        return ticketPriorityService.getTicketPriorityByName(priorityName);
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @GetMapping("/admin/ticketpriority/getdefault")
    public TicketPriority getDefaultTicketPriority() throws DefaultValueException {
        return defaultValueService.getDefaultPriorityValue();
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @PostMapping("/admin/ticketpriority/create")
    public TicketPriority createTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException{
        return ticketPriorityService.createTicketPriority(ticketPriority);
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @PutMapping("/admin/ticketpriority/update")
    public TicketPriority updateTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return ticketPriorityService.updateTicketPriority(ticketPriority);
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @PutMapping("/admin/ticketpriority/setasdefault")
    public TicketPriority setDefaultTicketPriority(@RequestBody TicketPriority ticketPriority) throws TicketPriorityException {
        return defaultValueService.setDefaultPriorityValue(ticketPriority);
    }

    @JsonView(CustomJsonView.TicketPriorityView.class)
    @DeleteMapping("/admin/ticketpriority/id/{priorityId}/delete")
    public void deleteTicketPriorityById (@PathVariable UUID priorityId){
        ticketPriorityService.deleteTicketPriorityById(priorityId);
    }

}
