package fr.alexandreklotz.quickdesk.backend.controller.admin;

import fr.alexandreklotz.quickdesk.backend.error.DeviceException;
import fr.alexandreklotz.quickdesk.backend.error.RolesException;
import fr.alexandreklotz.quickdesk.backend.error.TeamException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Utilisateur;
import fr.alexandreklotz.quickdesk.backend.service.UtilisateurService;
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

    @GetMapping("/admin/utilisateur/all")
    public List<Utilisateur> getAllUsers(){
        return utilisateurService.getAllUsers();
    }

    @GetMapping("/admin/utilisateur/id/{userId}")
    public Utilisateur getUserById(@PathVariable UUID userId) throws UtilisateurException {
        return utilisateurService.getUserById(userId);
    }

    @GetMapping("/admin/utilisateur/login/{userLogin}")
    public Utilisateur getUserByLogin(@PathVariable String userLogin) throws UtilisateurException{
        return utilisateurService.getUserByLogin(userLogin);
    }

    @PostMapping("/admin/utilisateur/create")
    public Utilisateur createUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        return utilisateurService.createUser(utilisateur);
    }

    @PutMapping("/admin/utilisateur/update")
    public Utilisateur updateUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        return utilisateurService.updateUser(utilisateur);
    }

    @DeleteMapping("/admin/utilisateur/id/{userId}/delete")
    public void deleteUserById(@PathVariable UUID userId) {
        utilisateurService.deleteUserById(userId);
    }
}
