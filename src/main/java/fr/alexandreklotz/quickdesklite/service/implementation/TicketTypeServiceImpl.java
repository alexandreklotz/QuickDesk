package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.TicketTypeException;
import fr.alexandreklotz.quickdesklite.model.TicketType;
import fr.alexandreklotz.quickdesklite.repository.TicketTypeRepository;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public TicketType getSpecifiedTicketType(TicketType ticketType) throws TicketTypeException {
        return ticketTypeRepository.findById(ticketType.getId()).orElseThrow(()
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
            throw new TicketTypeException("A ticket type already uses this name. Please specify another name or update the existing entity.");
        }

        if(ticketType.isDefault()){
            defaultValueService.setDefaultTypeValue(ticketType);
        }

        return ticketTypeRepository.saveAndFlush(ticketType);
    }

    @Override
    public TicketType updateTicketType(TicketType ticketType) throws TicketTypeException {
        Optional<TicketType> updatedType = ticketTypeRepository.findById(ticketType.getId());
        if(!updatedType.isPresent()){
            throw new TicketTypeException("The specified ticket type doesn't exist.");
        }

        Optional<TicketType> existingType = ticketTypeRepository.findTicketTypeValueByName(ticketType.getName());
        if(existingType.isPresent()){
           throw new TicketTypeException("A ticket type already uses this name. Please specify another name or update the existing entity.");
        }

        if(ticketType.isDefault()){
            defaultValueService.setDefaultTypeValue(updatedType.get());
        }

        updatedType.get().setName(ticketType.getName());
        return ticketTypeRepository.saveAndFlush(updatedType.get());
    }

    @Override
    public void deleteTicketType(TicketType ticketType) {
        ticketTypeRepository.delete(ticketType);
    }
}
