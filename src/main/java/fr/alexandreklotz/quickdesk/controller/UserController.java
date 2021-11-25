package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.UserDao;
import fr.alexandreklotz.quickdesk.model.User;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    private UserDao userDao;

    @Autowired
    UserController (UserDao userDao) {
        this.userDao = userDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.UserView.class)
    @PostMapping("/user/new")
    public void newUserCreation (@RequestBody User user) {

        user.setUserFirstName(user.getUserFirstName());
        user.setUserLastName(user.getUserLastName());
        user.setUserPassword(user.getUserPassword());
        user.setTeam(user.getTeam()); //TODO : Doesn't work when sending a JSON form. Won't assign an id to it, needs to be checked.
        user.setUserCreationDate(Date.from(Instant.now()));
        user.setUserIsEnabled(true);

        userDao.saveAndFlush(user);
    }



    @JsonView(CustomJsonView.UserView.class)
    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userDao.findAll());
    }



    @JsonView(CustomJsonView.UserView.class)
    @DeleteMapping("user/delete/{id}")
    public String deleteUser (@PathVariable int id){
        if (userDao.findById(id).isPresent()){
            String deletedUser = userDao.getById(id).getUserFirstName() + " " + userDao.getById(id).getUserLastName();
            userDao.deleteById(id);
            return "The user " + deletedUser + " has been successfully deleted.";
        } else {
            return "The specified user doesn't exist.";
        }
    }

}
