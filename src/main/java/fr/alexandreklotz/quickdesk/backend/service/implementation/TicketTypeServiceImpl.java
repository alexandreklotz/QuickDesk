package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.TicketTypeException;
import fr.alexandreklotz.quickdesk.backend.model.TicketType;
import fr.alexandreklotz.quickdesk.backend.repository.TicketTypeRepository;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {

    private TicketTypeRepository ticketTypeRepository;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketTypeServiceImpl(TicketTypeRepository ticketTypeRepository, DefaultValueService defaultValueService){
        this.ticketTypeRepository = ticketTypeRepository;
        this.defaultValueService = defaultValueService;
    }

    @Override
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    @Override
    public TicketType getTicketTypeById(UUID typeId) throws TicketTypeException {
        return ticketTypeRepository.findById(typeId).orElseThrow(()
        -> new TicketTypeException("The specified ticket type doesn't exist."));
    }

    @Override
    public TicketType getTicketTypeByName(String type) throws TicketTypeException {
        return ticketTypeRepository.findTicketTypeValueByName(type).orElseThrow(()
        -> new TicketTypeException(type + " doesn't match any existing ticket type."));
    }

    @Override
    public TicketType createTicketType(TicketType ticketType) throws TicketTypeException {
        Optional<TicketType> existingType = ticketTypeRepository.findTicketTypeValueByName(ticketType.getName());
        if(existingType.isPresent()){
            throw new TicketTypeException("ERROR : A ticket type already uses this name. Please specify another name or update the existing entity.");
        }

        if(ticketType.isDefault()){
            defaultValueService.setDefaultTypeValue(ticketType);
        }

        return ticketTypeRepository.saveAndFlush(ticketType);
    }

    @Override
    public TicketType updateTicketType(TicketType ticketType) throws TicketTypeException {
        Optional<TicketType> updatedType = ticketTypeRepository.findById(ticketType.getId());
        if(updatedType.isEmpty()){
            throw new TicketTypeException("ERROR : The specified ticket type " + ticketType.getName() + " doesn't exist.");
        }

        Optional<TicketType> existingType = ticketTypeRepository.findTicketTypeValueByName(ticketType.getName());
        if(existingType.isPresent()){
           throw new TicketTypeException("ERROR : A ticket type already uses this name. Please specify another name or update the existing entity.");
        }

        if(ticketType.isDefault()){
            defaultValueService.setDefaultTypeValue(ticketType);
        }

        return ticketTypeRepository.saveAndFlush(ticketType);
    }

    @Override
    public void deleteTicketTypeById(UUID typeId) {
        ticketTypeRepository.deleteById(typeId);
    }
}
