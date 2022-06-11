package fr.alexandreklotz.quickdesklite.service.implementation;

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
    public TicketType getSpecifiedTicketType(TicketType ticketType) {
        Optional<TicketType> type = ticketTypeRepository.findById(ticketType.getId());
        if(!type.isPresent()){
            return null;
        }
        return type.get();
    }

    @Override
    public TicketType getTicketTypeByName(String type) {
        Optional<TicketType> searchedType = ticketTypeRepository.findTicketTypeValueByName(type);
        if(!searchedType.isPresent()){
            return null; //doesn't exist
        }
        return searchedType.get();
    }

    @Override
    public TicketType createTicketType(TicketType ticketType) {
        Optional<TicketType> existingType = ticketTypeRepository.findTicketTypeValueByName(ticketType.getName());
        if(existingType.isPresent()){
            return null; //return error message saying a type with this name already exists.
        }
        if(ticketType.isDefault()){
            defaultValueService.setDefaultTypeValue(ticketType);
        }

        return ticketTypeRepository.saveAndFlush(ticketType);
    }

    @Override
    public TicketType updateTicketType(TicketType ticketType) {
        Optional<TicketType> updatedType = ticketTypeRepository.findById(ticketType.getId());
        if(!updatedType.isPresent()){
            return null; //not existing type
        }
        Optional<TicketType> existingType = ticketTypeRepository.findTicketTypeValueByName(ticketType.getName());
        if(existingType.isPresent()){
           return null; //error message, existing type with this name
        }
        if(ticketType.isDefault()){
            defaultValueService.setDefaultTypeValue(updatedType.get());
        }

        updatedType.get().setName(ticketType.getName());
        return updatedType.get();
    }

    @Override
    public void deleteTicketType(TicketType ticketType) {
        ticketTypeRepository.delete(ticketType);
    }
}
