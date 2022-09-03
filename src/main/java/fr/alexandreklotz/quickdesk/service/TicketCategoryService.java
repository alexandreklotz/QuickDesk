package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.TicketCategoryException;
import fr.alexandreklotz.quickdesk.model.TicketCategory;

import java.util.List;
import java.util.UUID;

public interface TicketCategoryService {

    public List<TicketCategory> getAllTicketCategories();

    public TicketCategory getTicketCategoryById(UUID catId) throws TicketCategoryException;

    public TicketCategory getTicketCategoryByName(String category) throws TicketCategoryException;

    public TicketCategory createTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException;

    public TicketCategory updateTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException;

    public void deleteTicketCategoryById(UUID catId);
}
