package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.error.TicketTypeException;
import fr.alexandreklotz.quickdesk.model.TicketType;
import fr.alexandreklotz.quickdesk.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.service.TicketTypeService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TicketTypeView.class)
    @GetMapping("/admin/tickettype/all")
    public List<TicketType> getAllTicketTypes(){
        return ticketTypeService.getAllTicketTypes();
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @GetMapping("/admin/tickettype/id/{typeId}")
    public TicketType getTicketTypeById(@PathVariable UUID typeId) throws TicketTypeException {
        return ticketTypeService.getTicketTypeById(typeId);
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @GetMapping("/admin/tickettype/name/{typeName}")
    public TicketType getTicketTypeByName(@PathVariable String typeName) throws TicketTypeException {
        return ticketTypeService.getTicketTypeByName(typeName);
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @GetMapping("/admin/tickettype/getdefault")
    public TicketType getDefaultType() throws DefaultValueException {
        return defaultValueService.getDefaultTypeValue();
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @PostMapping("/admin/tickettype/create")
    public TicketType createTicketType(@RequestBody TicketType ticketType) throws TicketTypeException {
        return ticketTypeService.createTicketType(ticketType);
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @PutMapping("/admin/tickettype/update")
    public TicketType updateTicketType(@RequestBody TicketType ticketType) throws TicketTypeException {
        return ticketTypeService.updateTicketType(ticketType);
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @PutMapping("/admin/tickettype/setasdefault")
    public TicketType setTicketTypeAsDefault(@RequestBody TicketType ticketType) throws TicketTypeException {
        return defaultValueService.setDefaultTypeValue(ticketType);
    }

    @JsonView(CustomJsonView.TicketTypeView.class)
    @DeleteMapping("/admin/tickettype/id/{typeId}/delete")
    public void deleteTicketTypeById(@PathVariable UUID typeId){
        ticketTypeService.deleteTicketTypeById(typeId);
    }
}
