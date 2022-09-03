package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.DeviceException;
import fr.alexandreklotz.quickdesk.error.RolesException;
import fr.alexandreklotz.quickdesk.error.TeamException;
import fr.alexandreklotz.quickdesk.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.model.Utilisateur;
import fr.alexandreklotz.quickdesk.service.UtilisateurService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @Autowired
    UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    ///////////////////
    // ADMIN METHODS //
    ///////////////////

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/all")
    public List<Utilisateur> getAllUsers(){
        return utilisateurService.getAllUsers();
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/id/{userId}")
    public Utilisateur getUserById(@PathVariable UUID userId) throws UtilisateurException {
        return utilisateurService.getUserById(userId);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/login/{userLogin}")
    public Utilisateur getUserByLogin(@PathVariable String userLogin) throws UtilisateurException{
        return utilisateurService.getUserByLogin(userLogin);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/admin/utilisateur/create")
    public Utilisateur createUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        return utilisateurService.createUser(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("/admin/utilisateur/update")
    public Utilisateur updateUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        return utilisateurService.updateUser(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @DeleteMapping("/admin/utilisateur/id/{userId}/delete")
    public void deleteUserById(@PathVariable UUID userId) {
        utilisateurService.deleteUserById(userId);
    }
}
