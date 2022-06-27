package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.TicketCategoryException;
import fr.alexandreklotz.quickdesklite.model.TicketCategory;

import java.util.List;

public interface TicketCategoryService {

    public List<TicketCategory> getAllTicketCategories();

    public TicketCategory getSpecifiedTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException;

    public TicketCategory getTicketCategoryByName(String category) throws TicketCategoryException;

    public TicketCategory createTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException;

    public TicketCategory updateTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException;

    public void deleteTicketCategory(TicketCategory ticketCategory);
}
