package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

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
    @GetMapping("/team/{teamid}")
    public ResponseEntity<Team> getSpecifiedTeam(@PathVariable Long teamid){

        Optional<Team> teamBdd = teamRepository.findById(teamid);

        if(teamBdd.isPresent()){
            return ResponseEntity.ok(teamBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PostMapping("/team/new")
    public void newTeam(@RequestBody Team team){

        if(team.getUtilisateurs() != null) {
            for(Utilisateur utilisateur : team.getUtilisateurs()){
                Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateur.getId());
                if(userBdd.isPresent()){
                    userBdd.get().setTeam(team);
                }
            }
        }

        team.setTeamDateCreated(LocalDateTime.now());

        teamRepository.saveAndFlush(team);
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PutMapping("/team/update/{teamId}")
    public ResponseEntity<String> updateTeam(@PathVariable Long teamId, @RequestBody Team team){

        Optional<Team> teamBdd = teamRepository.findById(teamId);
        if(teamBdd.isPresent()){

            if(team.getTeamName() != null) {
                teamBdd.get().setTeamName(team.getTeamName());
            }
            if(team.getTeamDesc() != null){
                teamBdd.get().setTeamDesc(team.getTeamDesc());
            }
            if(team.getUtilisateurs() != null){
                for(Utilisateur utilisateur : team.getUtilisateurs()){
                    Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateur.getId());
                    if(userBdd.isPresent()){
                        userBdd.get().setTeam(team);
                    }
                }
            }
            teamRepository.save(teamBdd.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.TeamView.class)
    @DeleteMapping("/team/delete/{teamId}")
    public String deleteTeam (@PathVariable Long teamId){

        Optional<Team> teamBdd = teamRepository.findById(teamId);
        if(teamBdd.isPresent()){
            String deletedTeam = teamBdd.get().getTeamName() + " has been deleted.";
            teamRepository.deleteById(teamId);
            return deletedTeam;
        } else {
            return "The specified team doesn't exist or an error occurred.";
        }
    }
}
