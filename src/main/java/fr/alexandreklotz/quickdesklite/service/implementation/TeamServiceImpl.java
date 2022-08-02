package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.TeamException;
import fr.alexandreklotz.quickdesklite.error.TicketQueueException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.TicketQueue;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketQueueRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.TeamService;
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
        -> new TeamException(teamid + " doesn't match any existing team."));
    }

    @Override
    public Team createNewTeam(Team team) throws UtilisateurException, TicketQueueException {

        /*if(team.getUtilisateurs() != null){
            for(Utilisateur utilisateur : team.getUtilisateurs()){
                Optional<Utilisateur> teamUser = utilisateurRepository.findById(utilisateur.getId());
                if(!teamUser.isPresent()){
                    throw new UtilisateurException("The specified user doesn't exist.");
                }
                teamUser.get().setTeam(team);
                utilisateurRepository.saveAndFlush(teamUser.get());
            }
        }*/

        /*if(team.getTicketQueues() != null){
            Set<TicketQueue> teamQueues = new HashSet<>();
            for(TicketQueue ticketQueue : team.getTicketQueues()){
                Optional<TicketQueue> tQueue = ticketQueueRepository.findById(ticketQueue.getId());
                if(!tQueue.isPresent()){
                    throw new TicketQueueException("The specified ticket queue with the id " + ticketQueue.getId() + " doesn't exist.");
                }
                teamQueues.add(tQueue.get());
                ticketQueueRepository.saveAndFlush(tQueue.get());
            }
            team.setTicketQueues(teamQueues);
        }*/

        //We save the date at which the team has been created
        team.setTeamDateCreated(LocalDateTime.now());

        teamRepository.saveAndFlush(team);
        return team;
    }

    @Override
    public Team updateTeam(Team team) throws TeamException, UtilisateurException, TicketQueueException {

        Optional<Team> updatedTeam = teamRepository.findById(team.getId());
        if(updatedTeam.isEmpty()){
            throw new TeamException("The specified team doesn't exist and therefore cannot be updated.");
        }

        team.setTeamDateCreated(updatedTeam.get().getTeamDateCreated());

        /*if(team.getUtilisateurs() != null){
            for(Utilisateur utilisateur : team.getUtilisateurs()){
                Optional<Utilisateur> teamUser = utilisateurRepository.findById(utilisateur.getId());
                if(teamUser.isEmpty()){
                    throw new UtilisateurException("The specified user doesn't exist and cannot be assigned to the team.");
                } else {
                    teamUser.get().setTeam(team);
                }
                utilisateurRepository.saveAndFlush(teamUser.get());
            }
        }

        Set<TicketQueue> teamQueues = updatedTeam.get().getTicketQueues();

        if(team.getTicketQueues() != null){
            for(TicketQueue ticketQueue : team.getTicketQueues()){
                Optional<TicketQueue> tQueue = ticketQueueRepository.findById(ticketQueue.getId());
                if(tQueue.isEmpty()){
                    throw new TicketQueueException("The specified ticket queue with the id " + ticketQueue.getId() + " doesn't exist.");
                } else {
                    teamQueues.add(tQueue.get());
                }
                ticketQueueRepository.saveAndFlush(tQueue.get());
            }
        }*/

        teamRepository.saveAndFlush(team);
        return team;
    }


    @Override
    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }
}
