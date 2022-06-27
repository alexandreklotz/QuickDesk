package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.*;
import fr.alexandreklotz.quickdesklite.model.*;
import fr.alexandreklotz.quickdesklite.repository.*;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultValueServiceImpl implements DefaultValueService {

    /* This service will contain methods to retrieve default values for ticket type, category, status, priority and queue.
    *
    * For the "Set" methods, it will first verify that the new default value sent to the API exists. If yes, then it will
    * create a list, retrieve all objects from this entity through the repository. If the list is empty, we set the requested value
    * as the new default, if the list isn't empty, we use a for each to iterate through the list and check the "isDefault" value
    * of each. Once we found the actual default value, we set the "isDefault" boolean to false and then set the new one's as true.
    * We save both objects through the saveAndFlush method. If there's no default value set, we then set the requested value as default.
    *
    * The "Get" methods will either use a @Query to directly find the isDefault value, or it will iterate through a list that will retrieve
    * all the objects of this entity.
    * */

    private TicketStatusRepository ticketStatusRepository;
    private TicketTypeRepository ticketTypeRepository;
    private TicketCategoryRepository ticketCategoryRepository;
    private TicketPriorityRepository ticketPriorityRepository;
    private TicketQueueRepository ticketQueueRepository;

    @Autowired
    DefaultValueServiceImpl(TicketStatusRepository ticketStatusRepository, TicketTypeRepository ticketTypeRepository, TicketCategoryRepository ticketCategoryRepository,
                            TicketPriorityRepository ticketPriorityRepository, TicketQueueRepository ticketQueueRepository){

        this.ticketStatusRepository = ticketStatusRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.ticketPriorityRepository = ticketPriorityRepository;
        this.ticketQueueRepository = ticketQueueRepository;
    }


    @Override
    public TicketStatus getDefaultStatusValue() throws DefaultValueException {
        Optional<TicketStatus> ticketStatus = ticketStatusRepository.findDefaultTicketStatusValue(true);
        if(!ticketStatus.isPresent()){
            throw new DefaultValueException("No default value is set for ticket status.");
        }
        return ticketStatus.get();
    }

    @Override
    public TicketStatus setDefaultStatusValue(TicketStatus ticketStatus) throws TicketStatusException {
        Optional<TicketStatus> currentDefault = ticketStatusRepository.findDefaultTicketStatusValue(true);
        if(!currentDefault.isPresent()){
            throw new TicketStatusException("The previous default value for ticket status doesn't exist or wasn't set.");
        }
        currentDefault.get().setDefault(false);
        ticketStatusRepository.saveAndFlush(currentDefault.get());

        Optional<TicketStatus> newDefaultStatusValue = ticketStatusRepository.findById(ticketStatus.getId());
        if(!newDefaultStatusValue.isPresent()){
            throw new TicketStatusException("The ticket status value you're trying to set as default doesn't exist.");
        }
        ticketStatusRepository.setDefaultTicketStatus(newDefaultStatusValue.get().getId());
        ticketStatusRepository.saveAndFlush(newDefaultStatusValue.get());

        return newDefaultStatusValue.get();
    }

    @Override
    public TicketCategory getDefaultCategoryValue() throws DefaultValueException {
        Optional<TicketCategory> ticketCategory = ticketCategoryRepository.findDefaultTicketCategory(true);
        if(!ticketCategory.isPresent()){
            throw new DefaultValueException("No default value is set for ticket category.");
        }
        return ticketCategory.get();
    }

    @Override
    public TicketCategory setDefaultCategoryValue(TicketCategory ticketCategory) throws TicketCategoryException {
        Optional<TicketCategory> currentDefault = ticketCategoryRepository.findDefaultTicketCategory(true);
        if(!currentDefault.isPresent()){
            throw new TicketCategoryException("The previous default value for ticket category doesn't exist or wasn't set.");
        }
        currentDefault.get().setDefault(false);
        ticketCategoryRepository.saveAndFlush(currentDefault.get());

        Optional<TicketCategory> newDefaultCategory = ticketCategoryRepository.findById(ticketCategory.getId());
        if(!newDefaultCategory.isPresent()){
            throw new TicketCategoryException("The ticket category value you're trying to set as default doesn't exist.");
        }
        ticketCategoryRepository.setDefaultTicketCategory(newDefaultCategory.get().getId());
        ticketCategoryRepository.saveAndFlush(newDefaultCategory.get());

        return newDefaultCategory.get();
    }

    @Override
    public TicketType getDefaultTypeValue() throws DefaultValueException {
        Optional<TicketType> ticketType = ticketTypeRepository.findDefaultTicketTypeValue(true);
        if(!ticketType.isPresent()){
            throw new DefaultValueException("No default value is set for ticket value.");
        }
        return ticketType.get();
    }

    @Override
    public TicketType setDefaultTypeValue(TicketType ticketType) throws TicketTypeException {
        Optional<TicketType> currentDefault = ticketTypeRepository.findDefaultTicketTypeValue(true);
        if(!currentDefault.isPresent()){
            throw new TicketTypeException("The previous default value for ticket type doesn't exist or wasn't set.");
        }
        currentDefault.get().setDefault(false);
        ticketTypeRepository.saveAndFlush(currentDefault.get());

        Optional<TicketType> newDefaultType = ticketTypeRepository.findById(ticketType.getId());
        if(!newDefaultType.isPresent()){
            throw new TicketTypeException("The ticket type value you're trying to set as default doesn't exist.");
        }
        ticketTypeRepository.setDefaultTicketType(newDefaultType.get().getId());
        ticketTypeRepository.saveAndFlush(newDefaultType.get());

        return newDefaultType.get();
    }

    @Override
    public TicketPriority getDefaultPriorityValue() throws DefaultValueException {
        Optional<TicketPriority> ticketPriority = ticketPriorityRepository.findDefaultTicketPriorityValue(true);
        if(!ticketPriority.isPresent()){
            throw new DefaultValueException("No default value is set for ticket priority.");
        }
        return ticketPriority.get();
    }

    @Override
    public TicketPriority setDefaultPriorityValue(TicketPriority ticketPriority) throws TicketPriorityException {
        Optional<TicketPriority> currentDefault = ticketPriorityRepository.findDefaultTicketPriorityValue(true);
        if(!currentDefault.isPresent()){
            throw new TicketPriorityException("The previous default value for ticket priority doesn't exist or wasn't set.");
        }
        currentDefault.get().setDefault(false);
        ticketPriorityRepository.saveAndFlush(currentDefault.get());

        Optional<TicketPriority> newDefaultPriority = ticketPriorityRepository.findById(ticketPriority.getId());
        if(!newDefaultPriority.isPresent()){
            throw new TicketPriorityException("The ticket priority value you're trying to set as default doesn't exist.");
        }
        ticketPriorityRepository.setDefaultTicketPriority(newDefaultPriority.get().getId());
        ticketPriorityRepository.saveAndFlush(newDefaultPriority.get());

        return newDefaultPriority.get();
    }

    @Override
    public TicketQueue getDefaultTicketQueue() throws DefaultValueException {
        Optional<TicketQueue> ticketQueue = ticketQueueRepository.findDefaultTicketQueueValue(true);
        if(!ticketQueue.isPresent()) {
            throw new DefaultValueException("No default value is set for ticket queue.");
        }
        return ticketQueue.get();
    }

    @Override
    public TicketQueue setDefaultTicketQueue(TicketQueue ticketQueue) throws TicketQueueException {
        Optional<TicketQueue> currentDefault = ticketQueueRepository.findDefaultTicketQueueValue(true);
        if(!currentDefault.isPresent()){
            throw new TicketQueueException("The previous default value for ticket queue doesn't exist or wasn't set.");
        }
        currentDefault.get().setDefault(false);
        ticketQueueRepository.saveAndFlush(currentDefault.get());

        Optional<TicketQueue> newDefaultQueue = ticketQueueRepository.findById(ticketQueue.getId());
        if(!newDefaultQueue.isPresent()){
            throw new TicketQueueException("The ticket queue value you're trying to set as default doesn't exist.");
        }
        ticketQueueRepository.setDefaultTicketQueue(newDefaultQueue.get().getId());
        ticketQueueRepository.saveAndFlush(newDefaultQueue.get());

        return newDefaultQueue.get();
    }
}
