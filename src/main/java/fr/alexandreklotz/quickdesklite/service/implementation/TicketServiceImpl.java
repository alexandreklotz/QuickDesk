package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private UtilisateurRepository utilisateurRepository;
    private UtilisateurServiceImpl utilisateurServiceImpl;
    private DefaultValueServiceImpl defaultValueServiceImpl;

    @Autowired
    TicketServiceImpl(TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, UtilisateurServiceImpl utilisateurServiceImpl, DefaultValueServiceImpl defaultValueServiceImpl){
        this.ticketRepository = ticketRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurServiceImpl = utilisateurServiceImpl;
        this.defaultValueServiceImpl = defaultValueServiceImpl;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //The two methods below are used to retrieve the tickets linked to the calling user.//
    //////////////////////////////////////////////////////////////////////////////////////

    public List<Ticket> getOpenedTickets(String login){
        UUID userId = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            userId = userBdd.get().getId();
        }

        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> openedTickets = new ArrayList<>();
        for(Ticket ticket : allTickets) {
            Utilisateur ticketUser = ticket.getUtilisateur();
            if (ticketUser.getId().equals(userId)) {
                openedTickets.add(ticket);
            }
        }
        return openedTickets;
    }


    public List<Ticket> getAssignedTickets(String login){
        UUID adminId = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            adminId = userBdd.get().getId();
        }
        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> assignedTickets = new ArrayList<>();
        for(Ticket ticket : allTickets){
            UUID assignedAdminId = ticket.getAssignedAdmin();
            if(assignedAdminId.equals(adminId)){
                assignedTickets.add(ticket);
            }
        }
        return assignedTickets;
    }

    //////////////////////////////////
    //The methods below are for CRUD//
    //////////////////////////////////

    //Create createTicket, updateTicket, getTicket (all and only one), deleteTicket.

    //Method to retrieve all tickets
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    //Method to retrieve a specific ticket
    public Ticket getSpecifiedTicket(Long number){
        Optional<Ticket> searchedTicket = ticketRepository.findTicketWithTicketNumber(number);
        if(searchedTicket.isPresent()){
            return searchedTicket.get();
        } else {
            return null; //Need to find a way to manage this.
        }
    }

    @Override
    public Ticket getTicketById(UUID ticketid) {
        Optional<Ticket> searchedTicket = ticketRepository.findById(ticketid);
        if(searchedTicket.isPresent()){
            return searchedTicket.get();
        } else {
            return null; //Find a way to return an error
        }
    }

    @Override
    public Ticket createUserTicket(Ticket ticket) {

        //We first set the default values
        ticket.setTicketStatus(defaultValueServiceImpl.getDefaultStatusValue());
        ticket.setTicketCategory(defaultValueServiceImpl.getDefaultCategoryValue());

        //Ticket will be editable
        ticket.setEditableTicket(true);

        //We set the creation date to now()
        ticket.setTicketDateCreated(LocalDateTime.now());

        //We assign the ticket to the default queue
        ticket.setTicketQueue(defaultValueServiceImpl.getDefaultTicketQueue());

        ticketRepository.saveAndFlush(ticket);
        return ticket;
    }

    @Override
    public Ticket createAdminTicket(Ticket ticket) {

        //We set automatic values such as time and editable ticket
        ticket.setTicketDateCreated(LocalDateTime.now());
        ticket.setEditableTicket(true);

        //We define conditions to manage empty fields
        if(ticket.getTicketStatus() == null){
            ticket.setTicketStatus(defaultValueServiceImpl.getDefaultStatusValue());
        }
        if(ticket.getTicketCategory() == null){
            ticket.setTicketCategory(defaultValueServiceImpl.getDefaultCategoryValue());
        }
        if(ticket.getTicketType() == null){
            ticket.setTicketType(defaultValueServiceImpl.getDefaultTypeValue());
        }
        if(ticket.getTicketPriority() == null){
            ticket.setTicketPriority(defaultValueServiceImpl.getDefaultPriorityValue());
        }
        if(ticket.getTicketQueue() == null){
            ticket.setTicketQueue(defaultValueServiceImpl.getDefaultTicketQueue());
        }

        if(ticket.getAssignedAdmin() != null){
            Optional<Utilisateur> assignedAdmin = utilisateurRepository.findById(ticket.getAssignedAdmin());
            if(assignedAdmin.isPresent()){
                ticket.setAssignedAdminName(assignedAdmin.get().toString());
            }
        }
        ticketRepository.saveAndFlush(ticket);

        return ticket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        Optional<Ticket> updatedTicket = ticketRepository.findById(ticket.getId());
        if(updatedTicket.isPresent()){

            //We save the date at which the ticket has been updated/modified
            updatedTicket.get().setTicketLastModified(LocalDateTime.now());

            //Manage empty fields -> If the fields specified below aren't empty, they will be updated. If they are empty, nothing will happen.
            //There might be a way to filter empty fields in the front end through JS scripts.
            if(ticket.getAssignedAdmin() != null){
                updatedTicket.get().setAssignedAdmin(ticket.getAssignedAdmin());
            }
            if(ticket.getTicketType() != null){
                updatedTicket.get().setTicketType(ticket.getTicketType());
            }
            if(ticket.getTicketStatus() != null){
                updatedTicket.get().setTicketStatus(ticket.getTicketStatus());
            }
            if(ticket.getTicketCategory() != null){
                updatedTicket.get().setTicketCategory(ticket.getTicketCategory());
            }
            if(ticket.getTicketPriority() != null){
                updatedTicket.get().setTicketPriority(ticket.getTicketPriority());
            }

            ticketRepository.saveAndFlush(updatedTicket.get());
        }
        return updatedTicket.get();
    }

    @Override
    public void closeTicket(Ticket ticket) {
        Optional<Ticket> closedTicket = ticketRepository.findById(ticket.getId());
        if(closedTicket.isPresent()){
            closedTicket.get().setEditableTicket(false);
        }
    }

    @Override
    public void deleteTicket(UUID ticketid) {
        ticketRepository.deleteById(ticketid);
    }
}
