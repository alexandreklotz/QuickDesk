package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.DeviceException;
import fr.alexandreklotz.quickdesklite.error.RolesException;
import fr.alexandreklotz.quickdesklite.error.TeamException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.service.UtilisateurService;
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

    @GetMapping("/admin/utilisateur/{userId}")
    public Utilisateur getUserById(@PathVariable UUID userId) throws UtilisateurException {
        return utilisateurService.getUserById(userId);
    }

    @GetMapping("/admin/utilisateur/{userLogin}")
    public Utilisateur getUserByLogin(@PathVariable String userLogin) throws UtilisateurException{
        return utilisateurService.getUserByLogin(userLogin);
    }

    @PostMapping("/admin/utilisateur/new")
    public Utilisateur createUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        return utilisateurService.createUser(utilisateur);
    }

    @PostMapping("/admin/utilisateur/update")
    public Utilisateur updateUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        return utilisateurService.updateUser(utilisateur);
    }

    @PostMapping("/admin/utilisateur/disable")
    public Utilisateur disableUser(@RequestBody Utilisateur utilisateur) throws UtilisateurException {
        return utilisateurService.disableUser(utilisateur);
    }

    @DeleteMapping("/admin/utilisateur/delete")
    public void deleteUser(@RequestBody Utilisateur utilisateur){
        utilisateurService.deleteUser(utilisateur);
    }
}
