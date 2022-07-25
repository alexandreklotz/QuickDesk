package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.DefaultValueException;
import fr.alexandreklotz.quickdesklite.error.TicketException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.DefaultValueService;
import fr.alexandreklotz.quickdesklite.service.TicketService;
import fr.alexandreklotz.quickdesklite.service.UtilisateurService;
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
    private UtilisateurService utilisateurService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketServiceImpl(TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, UtilisateurService utilisateurService, DefaultValueService defaultValueService){
        this.ticketRepository = ticketRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService = utilisateurService;
        this.defaultValueService = defaultValueService;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //The two methods below are used to retrieve the tickets linked to the calling user.//
    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Ticket> getOpenedTickets(String login) throws UtilisateurException {

        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(!userBdd.isPresent()){
            throw new UtilisateurException("The user doesn't exist, cannot retrieve any tickets.");
        }

        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> openedTickets = new ArrayList<>();

        for(Ticket ticket : allTickets) {
            Utilisateur ticketUser = ticket.getUtilisateur();
            if (ticketUser.getId().equals(userBdd.get().getId())) {
                openedTickets.add(ticket);
            }
        }
        return openedTickets;
    }


    @Override
    public List<Ticket> getAssignedTickets(String login) throws UtilisateurException {

        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(!userBdd.isPresent()){
            throw new UtilisateurException("The specified admin doesn't exist.");
        }
        List<Ticket> allTickets = ticketRepository.findAll();
        List<Ticket> assignedTickets = new ArrayList<>();
        for(Ticket ticket : allTickets){
            if(ticket.getAssignedAdmin().equals(userBdd.get().getId())){
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
    @Override
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    //Method to retrieve a specific ticket. It implements user's role verification in order to filter requests. Used by the front end.
    /*@Override
    public Ticket getTicketByNumber(Long ticketNbr, String login) throws UtilisateurException {
        Utilisateur callingUser = utilisateurService.getUserByLogin(login);
        Optional<Ticket> searchedTicket = ticketRepository.findTicketWithTicketNumber(ticketNbr);
        if(searchedTicket.isPresent()){
            boolean userRole = utilisateurService.isUserAdmin(login);
            if(!userRole){
                Utilisateur ticketAssignedUser = searchedTicket.get().getUtilisateur();
                if(!ticketAssignedUser.equals(callingUser)){
                    return null; //error, not allowed to access this ticket
                }
            }
            return searchedTicket.get();
        }
        return null; //verify this line
    }*/

    @Override
    public Ticket getTicketByNumber(Long ticketNbr, String login) throws TicketException, UtilisateurException {

        Optional<Ticket> searchedTicket = ticketRepository.findTicketWithTicketNumber(ticketNbr);
        Utilisateur callingUser = utilisateurService.getUserByLogin(login);
        boolean userRole = utilisateurService.isUserAdmin(login);

        if(userRole){
            if(!searchedTicket.isPresent()){
                throw new TicketException("The ticket with the number " + ticketNbr + " doesn't exist.");
            }
            return searchedTicket.get();
        }

        Utilisateur ticketAssignedUser = searchedTicket.get().getUtilisateur();
        if(!ticketAssignedUser.equals(callingUser)){
            throw new UtilisateurException("You're not allowed to access this ticket.");
        }

        return searchedTicket.get();
    }

    @Override
    public Ticket getTicketById(UUID ticketid) throws TicketException {
        return ticketRepository.findById(ticketid).orElseThrow(()
        -> new TicketException(ticketid + " doesn't match any existing ticket"));
    }

    @Override
    public Ticket createUserTicket(Ticket ticket) throws DefaultValueException {

        //We first set the default values
        ticket.setTicketStatus(defaultValueService.getDefaultStatusValue());
        ticket.setTicketCategory(defaultValueService.getDefaultCategoryValue());

        //We assign the ticket to the default queue
        ticket.setTicketQueue(defaultValueService.getDefaultTicketQueue());

        //Ticket will be editable
        ticket.setEditableTicket(true);

        //We set the creation date to now()
        ticket.setTicketDateCreated(LocalDateTime.now());

        ticketRepository.saveAndFlush(ticket);
        return ticket;
    }

    @Override
    public Ticket createAdminTicket(Ticket ticket) throws DefaultValueException, UtilisateurException {

        //We set automatic values such as time and editable ticket
        ticket.setTicketDateCreated(LocalDateTime.now());
        ticket.setEditableTicket(true);

        //We define conditions to manage empty fields
        if(ticket.getTicketStatus() == null){
            ticket.setTicketStatus(defaultValueService.getDefaultStatusValue());
        }
        if(ticket.getTicketCategory() == null){
            ticket.setTicketCategory(defaultValueService.getDefaultCategoryValue());
        }
        if(ticket.getTicketType() == null){
            ticket.setTicketType(defaultValueService.getDefaultTypeValue());
        }
        if(ticket.getTicketPriority() == null){
            ticket.setTicketPriority(defaultValueService.getDefaultPriorityValue());
        }
        if(ticket.getTicketQueue() == null){
            ticket.setTicketQueue(defaultValueService.getDefaultTicketQueue());
        }

        if(ticket.getAssignedAdmin() != null){
            Optional<Utilisateur> assignedAdmin = utilisateurRepository.findById(ticket.getAssignedAdmin());
            if(!assignedAdmin.isPresent()){
                throw new UtilisateurException("This admin doesn't exist.");
            }
            ticket.setAssignedAdminName(assignedAdmin.get().toString());
        }

        if(ticket.getUtilisateur() != null){
            Optional<Utilisateur> userTicket = utilisateurRepository.findById(ticket.getUtilisateur().getId());
            if(!userTicket.isPresent()){
                throw new UtilisateurException("The user you're trying to assign to this ticket doesn't exist.");
            }
            ticket.setUtilisateur(userTicket.get());
        }

        ticketRepository.saveAndFlush(ticket);
        return ticket;
    }

    //TODO : Update this method, make it more simple. It's probably possible to just save the updated ticket instead
    // of updating the existing one with the data from the updated one.
    @Override
    public Ticket updateTicket(Ticket ticket) throws TicketException {
        Optional<Ticket> updatedTicket = ticketRepository.findById(ticket.getId());
        if(!updatedTicket.isPresent()) {
            throw new TicketException("The ticket you're trying to update doesn't exist.");
        }

        //We save the date at which the ticket has been updated/modified
        updatedTicket.get().setTicketLastModified(LocalDateTime.now());

        //Manage empty fields -> If the fields specified below aren't empty, they will be updated. If they are empty, nothing will happen.
        //There might be a way to filter empty fields in the front end through JS scripts.
        if(ticket.getAssignedAdmin() != null){
            updatedTicket.get().setAssignedAdmin(ticket.getAssignedAdmin());
        }

        if(ticket.getTicketType() != null){
            updatedTicket.get().setTicketType(ticket.getTicketType());
        } else {
            throw new TicketException("You must specify the ticket type !");
        }

        if(ticket.getTicketStatus() != null){
            updatedTicket.get().setTicketStatus(ticket.getTicketStatus());
        } else {
            throw new TicketException("You must specify the ticket status !");
        }

        if(ticket.getTicketCategory() != null){
            updatedTicket.get().setTicketCategory(ticket.getTicketCategory());
        } else {
            throw new TicketException("You must specify the ticket category !");
        }

        if(ticket.getTicketPriority() != null){
            updatedTicket.get().setTicketPriority(ticket.getTicketPriority());
        } else {
            throw new TicketException("You must specify the ticket priority !");
        }

        ticketRepository.saveAndFlush(updatedTicket.get());
        return updatedTicket.get();
    }

    @Override
    public Ticket closeTicket(Ticket ticket) throws TicketException {
        Optional<Ticket> closedTicket = ticketRepository.findById(ticket.getId());
        if(!closedTicket.isPresent()){
            throw new TicketException("The ticket you're trying to close doesn't exist.");
        }
        closedTicket.get().setEditableTicket(false);
        ticketRepository.saveAndFlush(closedTicket.get());
        return closedTicket.get();
    }

    //TODO : This method needs to be tested.
    @Override
    public List<Ticket> getClosedTickets(boolean closed) {
        return ticketRepository.getClosedTickets(true);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }
}
