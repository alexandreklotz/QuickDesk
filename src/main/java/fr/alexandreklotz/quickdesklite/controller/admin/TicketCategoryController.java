package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.TicketCategoryException;
import fr.alexandreklotz.quickdesklite.model.TicketCategory;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketCategoryService;
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

    @GetMapping("/admin/ticketcategory/getspecifiedcategory")
    public TicketCategory getTicketCategoryById(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.getSpecifiedTicketCategory(ticketCategory);
    }

    @PostMapping("/admin/ticketcategory/new")
    public TicketCategory createTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.createTicketCategory(ticketCategory);
    }

    @PostMapping("/admin/ticketcategory/update")
    public TicketCategory updateTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.updateTicketCategory(ticketCategory);
    }

    @PostMapping("/admin/ticketcategory/setasdefault")
    public TicketCategory setTicketCategoryAsDefault(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return defaultValueService.setDefaultCategoryValue(ticketCategory);
    }

    @DeleteMapping("/admin/ticketcategory/delete")
    public void deleteTicketCategory(@RequestBody TicketCategory ticketCategory) {
        ticketCategoryService.deleteTicketCategory(ticketCategory);
    }


}
