package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.TeamDao;
import fr.alexandreklotz.quickdesk.dao.UserDao;
import fr.alexandreklotz.quickdesk.model.Team;
import fr.alexandreklotz.quickdesk.model.User;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TeamController {

    private TeamDao teamDao;
    private UserDao userDao;

    @Autowired
    TeamController(TeamDao teamDao, UserDao userDao){
        this.teamDao = teamDao;
        this.userDao = userDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TeamView.class)
    @PostMapping("/group/new")
    public void newTeamCreation(@RequestBody Team team){

        team.setTeamName(team.getTeamName());
        team.setTeamDescription(team.getTeamDescription());
        //team.setTeamUsersList(team.getTeamUsersList()); //There will be a method solely dedicated to that through the update function.
        team.setTeamDateCreated(Date.from(Instant.now()));
        team.setTeamEnabled(true);

        teamDao.saveAndFlush(team);
    }

    // TODO : Create a new update method to update an existing group

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
