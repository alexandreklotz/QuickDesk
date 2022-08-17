package fr.alexandreklotz.quickdesk.backend.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketCategoryException;
import fr.alexandreklotz.quickdesk.backend.model.TicketCategory;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketCategoryService;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
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

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @GetMapping("/admin/ticketcategory/all")
    public List<TicketCategory> getAllTicketCategories(){
        return ticketCategoryService.getAllTicketCategories();
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @GetMapping("/admin/ticketcategory/id/{catId}")
    public TicketCategory getTicketCategoryById(@PathVariable UUID catId) throws TicketCategoryException {
        return ticketCategoryService.getTicketCategoryById(catId);
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @GetMapping("/admin/ticketcategory/name/{catName}")
    public TicketCategory getTicketCategoryByName(@PathVariable String catName) throws TicketCategoryException {
        return ticketCategoryService.getTicketCategoryByName(catName);
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @GetMapping("/admin/ticketcategory/getdefault")
    public TicketCategory getDefaultTicketCategory() throws DefaultValueException {
        return defaultValueService.getDefaultCategoryValue();
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @PostMapping("/admin/ticketcategory/create")
    public TicketCategory createTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.createTicketCategory(ticketCategory);
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @PutMapping("/admin/ticketcategory/update")
    public TicketCategory updateTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryService.updateTicketCategory(ticketCategory);
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @PutMapping("/admin/ticketcategory/setasdefault")
    public TicketCategory setDefaultTicketCategory(@RequestBody TicketCategory ticketCategory) throws TicketCategoryException {
        return defaultValueService.setDefaultCategoryValue(ticketCategory);
    }

    @JsonView(CustomJsonView.TicketCategoryView.class)
    @DeleteMapping("/admin/ticketcategory/id/{catId}/delete")
    public void deleteTicketCategoryById(@PathVariable UUID catId) {
        ticketCategoryService.deleteTicketCategoryById(catId);
    }


}
