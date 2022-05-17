package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.TeamService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    TeamServiceImpl(TeamRepository teamRepository, UtilisateurRepository utilisateurRepository){
        this.teamRepository = teamRepository;
        this.utilisateurRepository = utilisateurRepository;
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
    public Team getSpecifiedTeam(UUID teamid) {
        Optional<Team> searchedTeam = teamRepository.findById(teamid);
        if(searchedTeam.isPresent()){
            return searchedTeam.get();
        } else {
            return null;
        }
    }

    @Override
    public Team createNewTeam(Team team) {

        //We save the date at which the team has been created
        team.setTeamDateCreated(LocalDateTime.now());

        teamRepository.saveAndFlush(team);
        return team;
    }

    @Override
    public Team updateTeam(Team team) {
        Optional<Team> updatedTeam = teamRepository.findById(team.getId());
        if(updatedTeam.isPresent()){
            if(team.getUtilisateurs() != null) {
                Set<Utilisateur> newTeamUsers = team.getUtilisateurs();
                updateTeamUsers(team, newTeamUsers);
            }
            teamRepository.saveAndFlush(updatedTeam.get());
        }
        return updatedTeam.get();
    }

    //TODO : The code below may not function as intended.
    @Override
    public Team updateTeamUsers(Team team, Set<Utilisateur> utilisateurs) {
        Optional<Team> updatedTeam = teamRepository.findById(team.getId());
        if(updatedTeam.isPresent()){
            for(Utilisateur utilisateur : utilisateurs){
                Optional<Utilisateur> user = utilisateurRepository.findById(utilisateur.getId());
                if(user.isPresent()){
                    user.get().setTeam(updatedTeam.get());
                    utilisateurRepository.saveAndFlush(utilisateur);
                    //teamRepository.saveAndFlush(team);
                }
            }
        }
        return team;
    }

    @Override
    public void deleteTeam(Team team) {
        teamRepository.deleteById(team.getId());
    }
}
