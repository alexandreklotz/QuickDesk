package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.TicketTypeException;
import fr.alexandreklotz.quickdesklite.model.TicketType;
import fr.alexandreklotz.quickdesklite.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketTypeController {

    private TicketTypeService ticketTypeService;

    @Autowired
    TicketTypeController(TicketTypeService ticketTypeService){
        this.ticketTypeService = ticketTypeService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @GetMapping("/admin/tickettype/all")
    public List<TicketType> getAllTicketTypes(){
        return ticketTypeService.getAllTicketTypes();
    }

    @GetMapping("/admin/tickettype/{typeId}")
    public TicketType getTicketTypeById(@PathVariable UUID typeId) throws TicketTypeException {
        return ticketTypeService.getTicketTypeById(typeId);
    }

    @GetMapping("/admin/tickettype/{typeName}")
    public TicketType getTicketTypeByName(@PathVariable String typeName) throws TicketTypeException {
        return ticketTypeService.getTicketTypeByName(typeName);
    }

    @PostMapping("/admin/tickettype/all")
    public TicketType createTicketType(@RequestBody TicketType ticketType) throws TicketTypeException {
        return ticketTypeService.createTicketType(ticketType);
    }

    @PostMapping("/admin/tickettype/update")
    public TicketType updateTicketType(@RequestBody TicketType ticketType) throws TicketTypeException {
        return ticketTypeService.updateTicketType(ticketType);
    }

    @DeleteMapping("/admin/tickettype/delete")
    public void deleteTicketType(@RequestBody TicketType ticketType){
        ticketTypeService.deleteTicketType(ticketType);
    }
}
