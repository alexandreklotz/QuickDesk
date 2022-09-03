package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.error.TicketStatusException;
import fr.alexandreklotz.quickdesk.model.TicketStatus;
import fr.alexandreklotz.quickdesk.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.service.TicketStatusService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketStatusController {

    private TicketStatusService ticketStatusService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketStatusController(TicketStatusService ticketStatusService, DefaultValueService defaultValueService){
        this.ticketStatusService = ticketStatusService;
        this.defaultValueService = defaultValueService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @JsonView(CustomJsonView.TicketStatusView.class)
    @GetMapping("/admin/ticketstatus/all")
    public List<TicketStatus> getAllTicketStatus(){
        return ticketStatusService.getAllTicketStatus();
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @GetMapping("/admin/ticketstatus/id/{statusId}")
    public TicketStatus getTicketStatusById(@PathVariable UUID statusId) throws TicketStatusException {
        return ticketStatusService.getTicketStatusById(statusId);
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @GetMapping("/admin/ticketstatus/name/{statusName}")
    public TicketStatus getTicketStatusByName(@PathVariable String statusName) throws TicketStatusException {
        return ticketStatusService.getTicketStatusByName(statusName);
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @GetMapping("/admin/ticketstatus/getdefault")
    public TicketStatus getDefaultTicketStatus() throws DefaultValueException {
        return defaultValueService.getDefaultStatusValue();
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @PostMapping("/admin/ticketstatus/create")
    public TicketStatus createTicketStatus(@RequestBody TicketStatus ticketStatus) throws TicketStatusException{
        return ticketStatusService.createTicketStatus(ticketStatus);
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @PutMapping("/admin/ticketstatus/update")
    public TicketStatus updateTicketStatus(@RequestBody TicketStatus ticketStatus) throws TicketStatusException {
        return ticketStatusService.updateTicketStatus(ticketStatus);
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @PutMapping("/admin/ticketstatus/setasdefault")
    public TicketStatus setDefaultTicketStatus(@RequestBody TicketStatus ticketStatus) throws TicketStatusException {
        return defaultValueService.setDefaultStatusValue(ticketStatus);
    }

    @JsonView(CustomJsonView.TicketStatusView.class)
    @DeleteMapping("/admin/ticketstatus/id/{queueId}/delete")
    public void deleteTicketStatusById(@PathVariable UUID queueId){
        ticketStatusService.deleteTicketStatusById(queueId);
    }
}
