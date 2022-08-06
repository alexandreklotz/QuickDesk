package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketCategoryException;
import fr.alexandreklotz.quickdesk.backend.model.TicketCategory;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class TicketCategoryController {

    private TicketCategoryService ticketCategoryService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketCategoryController(TicketCategoryService ticketCategoryService, DefaultValueService defaultValueService){
        this.ticketCategoryService = ticketCategoryService;
        this.defaultValueService = defaultValueService;
    }

    //////////////////
    // REST METHODS //
    //////////////////

    @GetMapping("/admin/ticketcategory/all")
    public List<TicketCategory> getAllTicketCategories(){
        return ticketCategoryService.getAllTicketCategories();
    }

    @GetMapping("/admin/ticketcategory/id/{catId}")
    public TicketCategory getTicketCategoryById(@PathVariable UUID catId) throws TicketCategoryException {
        return ticketCategoryService.getTicketCategoryById(catId);
    }

    @GetMapping("/admin/ticketcategory/name/{catName}")
    public TicketCategory getTicketCategoryByName(@PathVariable String catName) throws TicketCategoryException {
        return ticketCategoryService.getTicketCategoryByName(catName);
    }

    @GetMapping("/admin/ticketcategory/getdefault")
    public TicketCategory getDefaultTicketCategory() throws DefaultValueException {
        return defaultValueService.getDefaultCategoryValue();
    }

    @PostMapping("/admin/ticketcategory/new")
    public TicketCategory createTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.createTicketCategory(ticketCategory);
    }

    @PutMapping("/admin/ticketcategory/update")
    public TicketCategory updateTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.updateTicketCategory(ticketCategory);
    }

    @PutMapping("/admin/ticketcategory/setasdefault")
    public TicketCategory setDefaultTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return defaultValueService.setDefaultCategoryValue(ticketCategory);
    }

    @DeleteMapping("/admin/ticketcategory/id/{catId}/delete")
    public void deleteTicketCategoryById(@PathVariable UUID catId) {
        ticketCategoryService.deleteTicketCategoryById(catId);
    }


}
