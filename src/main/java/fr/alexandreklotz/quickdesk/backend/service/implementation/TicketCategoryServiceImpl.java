package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.TicketCategoryException;
import fr.alexandreklotz.quickdesk.backend.model.TicketCategory;
import fr.alexandreklotz.quickdesk.backend.repository.TicketCategoryRepository;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketCategoryServiceImpl implements TicketCategoryService {

    private TicketCategoryRepository ticketCategoryRepository;
    private DefaultValueService defaultValueService;


    @Autowired
    TicketCategoryServiceImpl(TicketCategoryRepository ticketCategoryRepository, DefaultValueService defaultValueService){
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.defaultValueService = defaultValueService;
    }

    @Override
    public List<TicketCategory> getAllTicketCategories() {
        return ticketCategoryRepository.findAll();
    }

    @Override
    public TicketCategory getTicketCategoryById(UUID catId) throws TicketCategoryException {
        return ticketCategoryRepository.findById(catId).orElseThrow(()
        -> new TicketCategoryException("ERROR : The ticket category with the id " + catId + " doesn't match any existing ticket category."));
    }

    @Override
    public TicketCategory getTicketCategoryByName(String category) throws TicketCategoryException {
        return ticketCategoryRepository.findTicketCategoryByName(category).orElseThrow(()
        -> new TicketCategoryException("ERROR : The ticket category " + category + " doesn't match any existing category"));
    }

    @Override
    public TicketCategory createTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException {

        Optional<TicketCategory> existingCategory = ticketCategoryRepository.findTicketCategoryByName(ticketCategory.getName());
        if(existingCategory.isPresent()){
           throw new TicketCategoryException("ERROR : A ticket category already uses this name. Please specify another name or modify the existing entity.");
        }

        if(ticketCategory.isDefault()){
            defaultValueService.setDefaultCategoryValue(ticketCategory);
        }

        return ticketCategoryRepository.saveAndFlush(ticketCategory);
    }

    @Override
    public TicketCategory updateTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException
    {
        Optional<TicketCategory> updatedCategory = ticketCategoryRepository.findById(ticketCategory.getId());
        if(updatedCategory.isEmpty()){
            throw new TicketCategoryException("ERROR : The category you're trying to update doesn't exist.");
        }

        Optional<TicketCategory> existingCategory = ticketCategoryRepository.findTicketCategoryByName(ticketCategory.getName());
        if(existingCategory.isPresent()){
            throw new TicketCategoryException("ERROR : A ticket category already uses this name. Please specify another name or modify the existing entity.");
        }

        if(ticketCategory.isDefault()){
            defaultValueService.setDefaultCategoryValue(ticketCategory);
        }

        return ticketCategoryRepository.saveAndFlush(ticketCategory);
    }

    @Override
    public void deleteTicketCategoryById(UUID catId) {
        ticketCategoryRepository.deleteById(catId);
    }
}
