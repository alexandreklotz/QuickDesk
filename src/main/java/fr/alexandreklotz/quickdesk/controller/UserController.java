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
public class UserController {

    private UserDao userDao;
    private TeamDao teamDao;

    @Autowired
    UserController (UserDao userDao, TeamDao teamDao) {
        this.userDao = userDao;
        this.teamDao = teamDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.UserView.class)
    @PostMapping("/user/new")
    public void newUserCreation (@RequestBody User user) {

        user.setUserPassword(user.getUserPassword());

        Team userteam = user.getTeam();
        Optional<Team> teamBdd = teamDao.findById(userteam.getTeamId());
        if (teamBdd.isPresent()){
            user.setTeam(userteam);
        }

        if(user.getUserType() == null) {
            user.setUserType(User.UserType.USER);
        }

        user.setUserCreationDate(Date.from(Instant.now()));
        user.setUserEnabled(true);

        userDao.saveAndFlush(user);
    }


    @JsonView(CustomJsonView.UserView.class)
    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userDao.findAll());
    }



    @JsonView(CustomJsonView.UserView.class)
    @DeleteMapping("user/delete/{userId}")
    public String deleteUser (@PathVariable int userId){

        Optional<User> userBdd = userDao.findById(userId);

        if (userBdd.isPresent()){
            String deletedUser = userBdd.get().getUserFirstName() + " " + userBdd.get().getUserLastName();
            userDao.deleteById(userId);
            return "The user " + deletedUser + " has been successfully deleted.";
        } else {
            return "The specified user doesn't exist.";
        }
    }

}
