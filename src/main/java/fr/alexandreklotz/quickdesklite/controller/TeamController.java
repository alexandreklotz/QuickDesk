package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private UtilisateurRepository utilisateurRepository;
    private TeamRepository teamRepository;

    @Autowired
    TeamController (UtilisateurRepository utilisateurRepository, TeamRepository teamRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.teamRepository = teamRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TeamView.class)
    @GetMapping("/team/all")
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PostMapping("/team/new")
    public void newTeam(@RequestBody Team team){

        team.setTeamDateCreated(Date.from(Instant.now()));

        teamRepository.saveAndFlush(team);
    }
}
