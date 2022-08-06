package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.DefaultValueException;
import fr.alexandreklotz.quickdesk.backend.error.TicketException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.*;
import fr.alexandreklotz.quickdesk.backend.repository.*;
import fr.alexandreklotz.quickdesk.backend.service.DefaultValueService;
import fr.alexandreklotz.quickdesk.backend.service.TicketService;
import fr.alexandreklotz.quickdesk.backend.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketTypeRepository ticketTypeRepository;
    private TicketCategoryRepository ticketCategoryRepository;
    private TicketQueueRepository ticketQueueRepository;
    private TicketStatusRepository ticketStatusRepository;
    private TicketPriorityRepository ticketPriorityRepository;
    private UtilisateurRepository utilisateurRepository;
    private UtilisateurService utilisateurService;
    private DefaultValueService defaultValueService;

    @Autowired
    TicketServiceImpl(TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository,
                      UtilisateurService utilisateurService, DefaultValueService defaultValueService,
                      TicketPriorityRepository ticketPriorityRepository, TicketCategoryRepository ticketCategoryRepository,
                      TicketStatusRepository ticketStatusRepository, TicketTypeRepository ticketTypeRepository,
                      TicketQueueRepository ticketQueueRepository){
        this.ticketRepository = ticketRepository;
        this.ticketPriorityRepository = ticketPriorityRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.ticketStatusRepository = ticketStatusRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketQueueRepository = ticketQueueRepository;
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
        if(userBdd.isEmpty()){
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
        if(userBdd.isEmpty()){
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
    @Override
    public Ticket getTicketByNumber(Long ticketNbr, String login) throws TicketException, UtilisateurException {

        Optional<Ticket> searchedTicket = ticketRepository.findTicketWithTicketNumber(ticketNbr);
        Utilisateur callingUser = utilisateurService.getUserByLogin(login);
        boolean userRole = utilisateurService.isUserAdmin(login);

        if(userRole){
            if(searchedTicket.isEmpty()){
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

        if(ticket.getUtilisateur() == null){
            throw new UtilisateurException("ERROR : The ticket " + ticket.getId() + " has no user assigned. You must assign a user to the ticket !");
        }

        ticketRepository.saveAndFlush(ticket);
        return ticket;
    }

    @Override
    public Ticket updateTicket(Ticket ticket) throws TicketException, DefaultValueException, UtilisateurException {
        Optional<Ticket> oldTicket = ticketRepository.findById(ticket.getId());
        if(oldTicket.isEmpty()){
            throw new TicketException("ERROR : Cannot update the ticket " + ticket.getId() + ". This ticket doesn't exist.");
        }

        //Ticket properties. The creation date is the date at which the original ticket has been created.
        ticket.setTicketDateCreated(oldTicket.get().getTicketDateCreated());
        ticket.setTicketLastModified(LocalDateTime.now());

        //I was planning to check every field at first but realized that it's useless considering that it will retrieve existing objects
        //from the API. It's useless to check.

        if(ticket.getUtilisateur() == null){
            throw new UtilisateurException("ERROR : " + ticket.getId() + " => You must assign a user to the ticket !");
        }

        //We then delete the old ticket and save the new one.
        ticketRepository.deleteById(oldTicket.get().getId());
        return ticketRepository.saveAndFlush(ticket);
    }

    //TODO : Check if useful or necessary, if not then delete
    @Override
    public Ticket closeTicket(Ticket ticket) throws TicketException {
        Optional<Ticket> closedTicket = ticketRepository.findById(ticket.getId());
        if(closedTicket.isEmpty()){
            throw new TicketException("The ticket you're trying to close doesn't exist.");
        }
        closedTicket.get().setEditableTicket(false);
        ticketRepository.saveAndFlush(closedTicket.get());
        return closedTicket.get();
    }

    //TODO : This method needs to be tested to see if necessary.
    @Override
    public List<Ticket> getClosedTickets(boolean closed) {
        return ticketRepository.getClosedTickets(true);
    }

    @Override
    public void deleteTicketById(UUID ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}