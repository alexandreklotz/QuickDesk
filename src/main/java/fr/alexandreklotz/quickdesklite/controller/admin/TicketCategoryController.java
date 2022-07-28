package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
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

    //TODO : fix url
    @GetMapping("/admin/ticketcategory/getspecifiedcategory")
    public TicketCategory getTicketCategoryById(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.getSpecifiedTicketCategory(ticketCategory);
    }

    @GetMapping("/admin/ticketcategory/getdefault")
    public TicketCategory getDefaultTicketCategory() throws DefaultValueException {
        return defaultValueService.getDefaultCategoryValue();
    }

    @PostMapping("/admin/ticketcategory/new")
    public TicketCategory createTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.createTicketCategory(ticketCategory);
    }

    @PostMapping("/admin/ticketcategory/update")
    public TicketCategory updateTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.updateTicketCategory(ticketCategory);
    }

    @DeleteMapping("/admin/ticketcategory/delete")
    public void deleteTicketCategory(@RequestBody TicketCategory ticketCategory) {
        ticketCategoryService.deleteTicketCategory(ticketCategory);
    }


}
