package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.TicketCategory;

import java.util.List;

public interface TicketCategoryService {

    public List<TicketCategory> getAllTicketCategories();

    public TicketCategory getSpecifiedTicketCategory(TicketCategory ticketCategory);

    public TicketCategory getTicketCategoryByName(String category);

    public TicketCategory createTicketCategory(TicketCategory ticketCategory);

    public TicketCategory updateTicketCategory(TicketCategory ticketCategory);

    public void deleteTicketCategory(TicketCategory ticketCategory);
}
