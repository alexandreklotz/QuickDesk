package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.TeamException;
import fr.alexandreklotz.quickdesk.backend.error.TicketQueueException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Team;
import fr.alexandreklotz.quickdesk.backend.model.TicketQueue;
import fr.alexandreklotz.quickdesk.backend.model.Utilisateur;
import fr.alexandreklotz.quickdesk.backend.repository.TeamRepository;
import fr.alexandreklotz.quickdesk.backend.repository.TicketQueueRepository;
import fr.alexandreklotz.quickdesk.backend.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesk.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private UtilisateurRepository utilisateurRepository;
    private TicketQueueRepository ticketQueueRepository;

    @Autowired
    TeamServiceImpl(TeamRepository teamRepository, UtilisateurRepository utilisateurRepository, TicketQueueRepository ticketQueueRepository){
        this.teamRepository = teamRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.ticketQueueRepository = ticketQueueRepository;
    }

    ////////////////////////
    // FUNCTIONAL METHODS //
    ////////////////////////

    @Override
    public boolean isTeamExisting(String teamName) {
        Optional<Team> team = teamRepository.getTeamByName(teamName);
        if(team.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    ////////
    //CRUD//
    ////////

    //Method to retrieve all teams
    @Override
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    @Override
    public Team getTeamById(UUID teamid) throws TeamException {
        return teamRepository.findById(teamid).orElseThrow(()
        -> new TeamException("ERROR : " + teamid + " doesn't match any existing team."));
    }

    @Override
    public Team getTeamByName(String name) throws TeamException {
        return teamRepository.getTeamByName(name).orElseThrow(()
        -> new TeamException("ERROR : The team " + name + " doesn't exist."));
    }

    @Override
    public Team createNewTeam(Team team) throws UtilisateurException, TicketQueueException, TeamException {

        boolean TeamExisting = isTeamExisting(team.getTeamName());
        if(TeamExisting){
            throw new TeamException("ERROR : The team " + team.getTeamName() + " already exists.");
        }

        Set<Utilisateur> usersTeam = new HashSet<>();

        if(team.getUtilisateurs() != null){
            for(Utilisateur utilisateur : team.getUtilisateurs()){
                Optional<Utilisateur> teamUser = utilisateurRepository.findById(utilisateur.getId());
                if(teamUser.isEmpty()){
                    throw new UtilisateurException("ERROR : The specified user doesn't exist and cannot be assigned to the team.");
                }
                usersTeam.add(teamUser.get());
            }
        }

        team.setUtilisateurs(usersTeam);

        Set<TicketQueue> teamQueues = team.getTicketQueues();

        if(team.getTicketQueues() != null){
            for(TicketQueue ticketQueue : team.getTicketQueues()){
                Optional<TicketQueue> tQueue = ticketQueueRepository.findById(ticketQueue.getId());
                if(tQueue.isEmpty()){
                    throw new TicketQueueException("ERROR : The specified ticket queue with the id " + ticketQueue.getId() + " doesn't exist.");
                }
                teamQueues.add(tQueue.get());
            }
        }

        //We save the date at which the team has been created
        team.setTeamDateCreated(LocalDateTime.now());

        teamRepository.saveAndFlush(team);
        return team;
    }

    @Override
    public Team updateTeam(Team team) throws TeamException, UtilisateurException, TicketQueueException{

        Optional<Team> updatedTeam = teamRepository.findById(team.getId());
        if(updatedTeam.isEmpty()){
            throw new TeamException("ERROR : The specified team doesn't exist and therefore cannot be updated.");
        }

        boolean TeamExisting = isTeamExisting(team.getTeamName());
        if(TeamExisting && !updatedTeam.get().getTeamName().equals(team.getTeamName())){
            throw new TeamException("ERROR : The team " + team.getTeamName() + " already exists.");
        }

        team.setTeamDateCreated(updatedTeam.get().getTeamDateCreated());

        Set<Utilisateur> usersTeam = new HashSet<>();

        if(team.getUtilisateurs() != null){
            for(Utilisateur utilisateur : team.getUtilisateurs()){
                Optional<Utilisateur> teamUser = utilisateurRepository.findById(utilisateur.getId());
                if(teamUser.isEmpty()){
                    throw new UtilisateurException("ERROR : The specified user doesn't exist and cannot be assigned to the team.");
                }
                usersTeam.add(teamUser.get());
            }
        }

        team.setUtilisateurs(usersTeam);

        Set<TicketQueue> teamQueues = updatedTeam.get().getTicketQueues();

        if(team.getTicketQueues() != null){
            for(TicketQueue ticketQueue : team.getTicketQueues()){
                Optional<TicketQueue> tQueue = ticketQueueRepository.findById(ticketQueue.getId());
                if(tQueue.isEmpty()){
                    throw new TicketQueueException("ERROR : The specified ticket queue with the id " + ticketQueue.getId() + " doesn't exist.");
                }
                teamQueues.add(tQueue.get());
            }
        }

        team.setTicketQueues(teamQueues);

        teamRepository.saveAndFlush(team);
        return team;
    }


    @Override
    public void deleteTeam(UUID teamId) {
        teamRepository.deleteById(teamId);
    }
}
