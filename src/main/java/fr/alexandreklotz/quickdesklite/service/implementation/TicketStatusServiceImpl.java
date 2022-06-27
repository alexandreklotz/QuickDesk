package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketStatusException;
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
    public TicketStatus getSpecifiedTicketStatus(TicketStatus ticketStatus) throws TicketStatusException {
        return ticketStatusRepository.findById(ticketStatus.getId()).orElseThrow(()
        -> new TicketStatusException("The specified ticket status doesn't exist."));
    }

    @Override
    public TicketStatus getTicketStatusByName(String status) throws TicketStatusException {
        return ticketStatusRepository.findTicketStatusByName(status).orElseThrow(()
        -> new TicketStatusException(status + " doesn't match any existing ticket status."));
    }

    @Override
    public TicketStatus createTicketStatus(TicketStatus ticketStatus) throws TicketStatusException {
        Optional<TicketStatus> existingStatus = ticketStatusRepository.findTicketStatusByName(ticketStatus.getName());
        if(existingStatus.isPresent()){
            throw new TicketStatusException("A ticket status already uses this name. Please specify another name or update the existing entity.");
        }
        if(ticketStatus.isDefault()){
            defaultValueService.setDefaultStatusValue(ticketStatus);
        }

        return ticketStatusRepository.saveAndFlush(ticketStatus);
    }

    @Override
    public TicketStatus updateTicketStatus(TicketStatus ticketStatus) throws TicketStatusException {

        Optional<TicketStatus> updatedStatus = ticketStatusRepository.findById(ticketStatus.getId());
        if(!updatedStatus.isPresent()){
            throw new TicketStatusException("The ticket status you're trying to update doesn't exist.");
        }

        Optional<TicketStatus> existingStatus = ticketStatusRepository.findTicketStatusByName(ticketStatus.getName());
        if(existingStatus.isPresent()){
            throw new TicketStatusException("A ticket status already uses this name. Please specify another name or update the existing entity.");
        }

        if(ticketStatus.isDefault()){
            defaultValueService.setDefaultStatusValue(updatedStatus.get());
        }

        if(ticketStatus.getName() != null){
            updatedStatus.get().setName(ticketStatus.getName());
        }

        return ticketStatusRepository.saveAndFlush(updatedStatus.get());
    }

    @Override
    public void deleteTicketStatus(TicketStatus ticketStatus) {
        ticketStatusRepository.delete(ticketStatus);
    }
}
