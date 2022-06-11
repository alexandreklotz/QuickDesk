package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.TicketStatus;
import fr.alexandreklotz.quickdesklite.repository.TicketStatusRepository;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketStatusServiceImpl implements TicketStatusService {

    private TicketStatusRepository ticketStatusRepository;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketStatusServiceImpl(TicketStatusRepository ticketStatusRepository, DefaultValueService defaultValueService){
        this.ticketStatusRepository = ticketStatusRepository;
        this.defaultValueService = defaultValueService;
    }


    @Override
    public List<TicketStatus> getAllTicketStatus() {
        return ticketStatusRepository.findAll();
    }

    @Override
    public TicketStatus getSpecifiedTicketStatus(TicketStatus ticketStatus) {
        Optional<TicketStatus> searchedStatus = ticketStatusRepository.findById(ticketStatus.getId());
        if(!searchedStatus.isPresent()){
            return null; //non existing status
        }
        return searchedStatus.get();
    }

    @Override
    public TicketStatus getTicketStatusByName(String status) {
        Optional<TicketStatus> searchedStatus = ticketStatusRepository.findTicketStatusByName(status);
        if(!searchedStatus.isPresent()){
            return null; //doesn't exist
        }
        return searchedStatus.get();
    }

    @Override
    public TicketStatus createTicketStatus(TicketStatus ticketStatus) {
        Optional<TicketStatus> existingStatus = ticketStatusRepository.findTicketStatusByName(ticketStatus.getName());
        if(existingStatus.isPresent()){
            return null; //a status with this name already exists
        }
        if(ticketStatus.isDefault()){
            defaultValueService.setDefaultStatusValue(ticketStatus);
        }

        return ticketStatusRepository.saveAndFlush(ticketStatus);
    }

    @Override
    public TicketStatus updateTicketStatus(TicketStatus ticketStatus) {
        Optional<TicketStatus> updatedStatus = ticketStatusRepository.findById(ticketStatus.getId());
        if(!updatedStatus.isPresent()){
            return null; //the status you're trying to update doesn't exist
        }
        Optional<TicketStatus> existingStatus = ticketStatusRepository.findTicketStatusByName(ticketStatus.getName());
        if(existingStatus.isPresent()){
            return null; //this name is already assigned to another status
        }
        if(ticketStatus.isDefault()){
            defaultValueService.setDefaultStatusValue(updatedStatus.get());
        }

        updatedStatus.get().setName(ticketStatus.getName());
        return ticketStatusRepository.saveAndFlush(updatedStatus.get());
    }

    @Override
    public void deleteTicketStatus(TicketStatus ticketStatus) {
        ticketStatusRepository.delete(ticketStatus);
    }
}
