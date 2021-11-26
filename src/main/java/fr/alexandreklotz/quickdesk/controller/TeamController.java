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
        team.setTeamUsersList(team.getTeamUsersList());
        team.setTeamDateCreated(Date.from(Instant.now()));

        team.setTeamEnabled(true);

        teamDao.saveAndFlush(team);
    }

    // TODO
    /*
    @JsonView(CustomJsonView.TeamView.class)
    @PatchMapping("/group/update/{id}")
    public String addTeamUsers(@PathVariable int id){

        User user = userDao.getById(id);
        String userName = user.getUserFirstName() + " " + user.getUserLastName();
        Optional<Team> teamBdd = teamDao.findById(id);
        String groupName = teamBdd.get().getTeamName();

        if(teamBdd.isPresent()){
            List<User> teamusers = teamBdd.get().getTeamUsersList();
            teamusers.add(user);
            return "The user " + userName + " has been successfully added to the " + groupName + " group.";
        } else if (userDao.findById(user.getUserId()).isEmpty()){
            return "The specified user doesn't exist.";
        } else if (teamBdd.isEmpty()) {
            return "The specified team doesn't exist.";
        } else {
            return "Unknown error. Make sure that all the fields have been properly specified.";
        }
    }*/


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
