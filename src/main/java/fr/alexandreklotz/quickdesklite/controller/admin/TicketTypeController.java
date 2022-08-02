package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketTypeException;
import fr.alexandreklotz.quickdesklite.model.TicketType;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketTypeController {

    private TicketTypeService ticketTypeService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketTypeController(TicketTypeService ticketTypeService, DefaultValueService defaultValueService){
        this.ticketTypeService = ticketTypeService;
        this.defaultValueService = defaultValueService;
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

    @GetMapping("/admin/tickettype/getdefault")
    public TicketType getDefaultType() throws DefaultValueException {
        return defaultValueService.getDefaultTypeValue();
    }

    @PostMapping("/admin/tickettype/all")
    public TicketType createTicketType(@RequestBody TicketType ticketType) throws TicketTypeException {
        return ticketTypeService.createTicketType(ticketType);
    }

    @PutMapping("/admin/tickettype/update")
    public TicketType updateTicketType(@RequestBody TicketType ticketType) throws TicketTypeException {
        return ticketTypeService.updateTicketType(ticketType);
    }

    @PutMapping("/admin/tickettype/setasdefault")
    public TicketType setTicketTypeAsDefault(@RequestBody TicketType ticketType) throws TicketTypeException {
        return defaultValueService.setDefaultTypeValue(ticketType);
    }

    @DeleteMapping("/admin/tickettype/delete")
    public void deleteTicketType(@RequestBody TicketType ticketType){
        ticketTypeService.deleteTicketType(ticketType);
    }
}
