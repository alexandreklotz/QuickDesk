package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.TeamDao;
import fr.alexandreklotz.quickdesk.model.Team;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private TeamDao teamDao;

    @Autowired
    TeamController(TeamDao teamDao){
        this.teamDao = teamDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TeamView.class)
    @PostMapping("/group/new")
    public void newTeamCreation(@RequestBody Team team){

        team.setTeamName(team.getTeamName());
        team.setTeamDescription(team.getTeamDescription());
        team.setTeamUsersList(team.getTeamUsersList());
        team.setTeamDateCreated(Date.from(Instant.now()));

        team.setIsTeamEnabled(true);

        teamDao.saveAndFlush(team);
    }


    @JsonView(CustomJsonView.TeamView.class)
    @GetMapping("/group/all")
    public ResponseEntity<List<Team>> getAllTeams () {
        return ResponseEntity.ok(teamDao.findAll());
    }



    @JsonView(CustomJsonView.TeamView.class)
    @DeleteMapping("/group/delete/{id}")
    public String teamDelete (@PathVariable int id){
        if(teamDao.findById(id).isPresent()){
            String deletedTeam = teamDao.findById(id).get().getTeamName();
            teamDao.deleteById(id);
            return "The " + deletedTeam + " group has been successfully deleted.";
        } else {
            return "The specified group doesn't exist.";
        }
    }

}
