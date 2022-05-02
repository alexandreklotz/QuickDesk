package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    TeamService(TeamRepository teamRepository, UtilisateurRepository utilisateurRepository){
        this.teamRepository = teamRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    ////////
    //CRUD//
    ////////

    //Method to retrieve all teams
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }
}
