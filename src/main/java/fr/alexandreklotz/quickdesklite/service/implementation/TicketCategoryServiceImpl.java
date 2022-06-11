package fr.alexandreklotz.quickdesklite.service.implementation;

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
    public TicketCategory getSpecifiedTicketCategory(TicketCategory ticketCategory) {
        Optional<TicketCategory> searchedCategory = ticketCategoryRepository.findById(ticketCategory.getId());
        if(!searchedCategory.isPresent()){
            return null; //non existent
        }
        return searchedCategory.get();
    }

    @Override
    public TicketCategory getTicketCategoryByName(String category) {
        Optional<TicketCategory> searchedCategory = ticketCategoryRepository.findTicketCategoryByName(category);
        if(!searchedCategory.isPresent()){
            return null; //doesn't exist
        }
        return searchedCategory.get();
    }

    @Override
    public TicketCategory createTicketCategory(TicketCategory ticketCategory) {
        Optional<TicketCategory> existingCategory = ticketCategoryRepository.findTicketCategoryByName(ticketCategory.getName());
        if(existingCategory.isPresent()){
           return null; //existing category
        }
        if(ticketCategory.isDefault()){
            defaultValueService.setDefaultCategoryValue(ticketCategory);
        }

        return ticketCategoryRepository.saveAndFlush(ticketCategory);
    }

    @Override
    public TicketCategory updateTicketCategory(TicketCategory ticketCategory) {
        Optional<TicketCategory> updatedCategory = ticketCategoryRepository.findById(ticketCategory.getId());
        if(!updatedCategory.isPresent()){
            return null; //the category you're trying to update doesn't exist.
        }
        Optional<TicketCategory> existingCategory = ticketCategoryRepository.findTicketCategoryByName(ticketCategory.getName());
        if(existingCategory.isPresent()){
            return null; //a category with this name already exists
        }
        if(ticketCategory.isDefault()){
            defaultValueService.setDefaultCategoryValue(updatedCategory.get());
        }

        updatedCategory.get().setName(ticketCategory.getName());

        return ticketCategoryRepository.saveAndFlush(updatedCategory.get());
    }

    @Override
    public void deleteTicketCategory(TicketCategory ticketCategory) {
        ticketCategoryRepository.delete(ticketCategory);
    }
}
