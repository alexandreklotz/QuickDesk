package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.TicketCategoryException;
import fr.alexandreklotz.quickdesklite.model.TicketCategory;
import fr.alexandreklotz.quickdesklite.repository.TicketCategoryRepository;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public TicketCategory getSpecifiedTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException {
        return ticketCategoryRepository.findById(ticketCategory.getId()).orElseThrow(()
        -> new TicketCategoryException("The ticket category with the id " + ticketCategory.getId() + " doesn't match any existing ticket category."));
    }

    @Override
    public TicketCategory getTicketCategoryByName(String category) throws TicketCategoryException {
        return ticketCategoryRepository.findTicketCategoryByName(category).orElseThrow(()
        -> new TicketCategoryException(category + " doesn't match any existing category"));
    }

    @Override
    public TicketCategory createTicketCategory(TicketCategory ticketCategory) throws TicketCategoryException {
        Optional<TicketCategory> existingCategory = ticketCategoryRepository.findTicketCategoryByName(ticketCategory.getName());
        if(existingCategory.isPresent()){
           throw new TicketCategoryException("A ticket category already uses this name. Please specify another name or modify the existing entity.");
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
        if(!updatedCategory.isPresent()){
            throw new TicketCategoryException("The category you're trying to update doesn't exist.");
        }

        Optional<TicketCategory> existingCategory = ticketCategoryRepository.findTicketCategoryByName(ticketCategory.getName());
        if(existingCategory.isPresent()){
            throw new TicketCategoryException("A ticket category already uses this name. Please specify another name or modify the existing entity.");
        }

        if(ticketCategory.isDefault()){
            defaultValueService.setDefaultCategoryValue(updatedCategory.get());
        }

        if(ticketCategory.getName() != null){
            updatedCategory.get().setName(ticketCategory.getName());
        }

        if(ticketCategory.getName() != null){
            updatedCategory.get().setName(ticketCategory.getName());
        }

        return ticketCategoryRepository.saveAndFlush(updatedCategory.get());
    }

    @Override
    public void deleteTicketCategory(TicketCategory ticketCategory) {
        ticketCategoryRepository.delete(ticketCategory);
    }
}
