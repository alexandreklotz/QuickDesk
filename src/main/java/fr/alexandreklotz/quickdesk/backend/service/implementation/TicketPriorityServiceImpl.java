package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.TicketPriorityException;
import fr.alexandreklotz.quickdesk.backend.model.TicketPriority;
import fr.alexandreklotz.quickdesk.backend.repository.TicketPriorityRepository;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public TicketPriority getTicketPriorityById(UUID priorityId) throws TicketPriorityException {
        return ticketPriorityRepository.findById(priorityId).orElseThrow(()
        -> new TicketPriorityException("ERROR : " + priorityId + " doesn't match any existing ticket priority."));
    }

    @Override
    public TicketPriority getTicketPriorityByName(String priority) throws TicketPriorityException{
        return ticketPriorityRepository.findTicketPriorityWithName(priority).orElseThrow(()
        -> new TicketPriorityException(priority + " doesn't match any existing entity."));
    }

    @Override
    public TicketPriority createTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException{
        Optional<TicketPriority> existingPriority = ticketPriorityRepository.findTicketPriorityWithName(ticketPriority.getName());
        if(existingPriority.isPresent()){
            throw new TicketPriorityException("ERROR : A ticket priority already uses this name. Please specify another name or update the existing entity.");
        }
        if(ticketPriority.isDefault()){
            defaultValueService.setDefaultPriorityValue(ticketPriority);
        }

        return ticketPriorityRepository.saveAndFlush(ticketPriority);
    }

    @Override
    public TicketPriority updateTicketPriority(TicketPriority ticketPriority) throws TicketPriorityException{

        Optional<TicketPriority> updatedPriority = ticketPriorityRepository.findById(ticketPriority.getId());
        if(updatedPriority.isEmpty()){
            throw new TicketPriorityException("ERROR : The priority " + ticketPriority.getName() + " you're trying to update doesn't exist");
        }

        Optional<TicketPriority> existingPriority = ticketPriorityRepository.findTicketPriorityWithName(ticketPriority.getName());
        if(existingPriority.isPresent()){
            throw new TicketPriorityException("ERROR : A ticket priority already uses this name. Please specify another name or update the existing entity.");
        }
        if(ticketPriority.isDefault()){
            defaultValueService.setDefaultPriorityValue(ticketPriority);
        }

        return ticketPriorityRepository.saveAndFlush(ticketPriority);
    }

    @Override
    public void deleteTicketPriorityById(UUID priorityId) {
        ticketPriorityRepository.deleteById(priorityId);
    }
}
