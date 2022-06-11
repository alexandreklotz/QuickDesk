package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.TicketPriority;
import fr.alexandreklotz.quickdesklite.repository.TicketPriorityRepository;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketPriorityServiceImpl implements TicketPriorityService {

    private TicketPriorityRepository ticketPriorityRepository;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketPriorityServiceImpl(TicketPriorityRepository ticketPriorityRepository, DefaultValueService defaultValueService){
        this.ticketPriorityRepository = ticketPriorityRepository;
        this.defaultValueService = defaultValueService;
    }


    @Override
    public List<TicketPriority> getAllTicketPriorities() {
        return ticketPriorityRepository.findAll();
    }

    @Override
    public TicketPriority getSpecifiedTicketPriority(TicketPriority ticketPriority) {
        Optional<TicketPriority> searchedPriority  = ticketPriorityRepository.findById(ticketPriority.getId());
        if(!searchedPriority.isPresent()){
            return null; //not existing
        }
        return searchedPriority.get();
    }

    @Override
    public TicketPriority getTicketPriorityByName(String priority) {
        Optional<TicketPriority> searchedPriority = ticketPriorityRepository.findTicketPriorityWithName(priority);
        if(!searchedPriority.isPresent()){
            return null; //doesn't exist
        }
        return searchedPriority.get();
    }

    @Override
    public TicketPriority createTicketPriority(TicketPriority ticketPriority) {
        Optional<TicketPriority> existingPriority = ticketPriorityRepository.findTicketPriorityWithName(ticketPriority.getName());
        if(existingPriority.isPresent()){
            return null; //return a priority already exists with this name
        }
        if(ticketPriority.isDefault()){
            defaultValueService.setDefaultPriorityValue(ticketPriority);
        }

        return ticketPriorityRepository.saveAndFlush(ticketPriority);
    }

    @Override
    public TicketPriority updateTicketPriority(TicketPriority ticketPriority) {
        Optional<TicketPriority> updatedPriority = ticketPriorityRepository.findById(ticketPriority.getId());
        if(!updatedPriority.isPresent()){
            return null; //the priority you're trying to update doesn't exist
        }
        Optional<TicketPriority> existingPriority = ticketPriorityRepository.findTicketPriorityWithName(ticketPriority.getName());
        if(existingPriority.isPresent()){
            return null; //a priority already exists with this name
        }
        if(ticketPriority.isDefault()){
            defaultValueService.setDefaultPriorityValue(updatedPriority.get());
        }

        updatedPriority.get().setName(ticketPriority.getName());
        return ticketPriorityRepository.saveAndFlush(updatedPriority.get());
    }

    @Override
    public void deleteTicketPriority(TicketPriority ticketPriority) {
        ticketPriorityRepository.delete(ticketPriority);
    }
}
