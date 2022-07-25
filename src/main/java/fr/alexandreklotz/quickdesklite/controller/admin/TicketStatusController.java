package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.TicketStatusException;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.TicketStatus;
import fr.alexandreklotz.quickdesklite.service.TicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketStatusController {

    private TicketStatusService ticketStatusService;

    @Autowired
    TicketStatusController(TicketStatusService ticketStatusService){
        this.ticketStatusService = ticketStatusService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/ticketstatus/all")
    public List<TicketStatus> getAllTicketStatus(){
        return ticketStatusService.getAllTicketStatus();
    }

    @GetMapping("/admin/ticketstatus/{statusId}")
    public TicketStatus getTicketStatusById(@PathVariable UUID statusId) throws TicketStatusException {
        return ticketStatusService.getTicketStatusById(statusId);
    }

    @GetMapping("/admin/ticketstatus/{statusName}")
    public TicketStatus getTicketStatusByName(@PathVariable String statusName) throws TicketStatusException {
        return ticketStatusService.getTicketStatusByName(statusName);
    }

    @PostMapping("/admin/ticketstatus/new")
    public TicketStatus createTicketStatus(@RequestBody TicketStatus ticketStatus) throws TicketStatusException{
        return ticketStatusService.createTicketStatus(ticketStatus);
    }

    @PostMapping("/admin/ticketstatus/update")
    public TicketStatus updateTicketStatus(@RequestBody TicketStatus ticketStatus) throws TicketStatusException {
        return ticketStatusService.updateTicketStatus(ticketStatus);
    }

    @DeleteMapping("/admin/ticketstatus/delete")
    public void deleteTicketStatus(@RequestBody TicketStatus ticketStatus){
        ticketStatusService.deleteTicketStatus(ticketStatus);
    }
}
