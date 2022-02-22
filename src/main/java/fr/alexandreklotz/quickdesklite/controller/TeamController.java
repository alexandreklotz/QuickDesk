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
    @GetMapping("/admin/team/all")
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @JsonView(CustomJsonView.TeamView.class)
    @GetMapping("/admin/team/{teamid}")
    public ResponseEntity<Team> getSpecifiedTeam(@PathVariable UUID teamid){

        Optional<Team> teamBdd = teamRepository.findById(teamid);

        if(teamBdd.isPresent()){
            return ResponseEntity.ok(teamBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PostMapping("/admin/team/new")
    public ResponseEntity<String> newTeam(@RequestBody Team team){

        //We check if the utilisateurs list is null, if it isn't then we check if every user in the list exists by using the utilisateurrepository. If it does, he will be assigned to the team.
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
        return ResponseEntity.ok(team.getTeamName() + " has been successfully created.");
    }

    @JsonView(CustomJsonView.TeamView.class)
    @PutMapping("/admin/team/update/{teamId}")
    public ResponseEntity<String> updateTeam(@PathVariable UUID teamId, @RequestBody Team team){

        //We first checj if the team exists, if it does we can then update the desired fields.
        //The process with the users list is the same that the one in the Post request/creation process.
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
                        userBdd.get().setTeam(teamBdd.get());
                    }
                }
            }
            teamRepository.save(teamBdd.get());
            return ResponseEntity.ok().body(teamBdd.get().getTeamName() + " has been successfully updated.");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.TeamView.class)
    @DeleteMapping("/admin/team/delete/{teamId}")
    public String deleteTeam (@PathVariable UUID teamId){

        //We check if the team exists, if it does we can then process with its deletion.
        Optional<Team> teamBdd = teamRepository.findById(teamId);
        if(teamBdd.isPresent()){
            Set<Utilisateur> teamUsers = teamBdd.get().getUtilisateurs();
            for(Utilisateur utilisateur : teamUsers) {
                Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateur.getId());
                if(userBdd.isPresent()){
                    userBdd.get().setTeam(null);
                }
            }
            String deletedTeam = teamBdd.get().getTeamName() + " has been deleted.";
            teamRepository.deleteById(teamId);
            return deletedTeam;
        } else {
            return "The specified team doesn't exist or an error occurred.";
        }
    }
}
