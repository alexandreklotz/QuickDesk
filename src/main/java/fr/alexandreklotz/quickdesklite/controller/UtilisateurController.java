package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.service.UtilisateurService;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @Autowired
    UtilisateurController (UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/all")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(){
        return ResponseEntity.ok(utilisateurService.getAllUsers());
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/{utilisateurid}")
    public ResponseEntity<Utilisateur> getSpecifiedUtilisateur(@PathVariable UUID utilisateurid){
        return ResponseEntity.ok(utilisateurService.getUserById(utilisateurid));
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/{login}")
    public ResponseEntity<Utilisateur> getSpecifiedUserWithLogin(@PathVariable String login){
        return ResponseEntity.ok(utilisateurService.getUserByLogin(login));
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/admin/utilisateur/new")
    public ResponseEntity<String> newUtilisateur(@RequestBody Utilisateur utilisateur){
        return ResponseEntity.ok(utilisateurService.createUser(utilisateur));
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("/admin/utilisateur/update/{utilisateurId}")
    public ResponseEntity<String> updateUtilisateur (@RequestBody Utilisateur utilisateur){
        return ResponseEntity.ok(utilisateurService.updateUser(utilisateur));
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @DeleteMapping("/admin/utilisateur/delete/{utilisateurId}")
    public ResponseEntity<String> deleteUtilisateur (@PathVariable UUID utilisateurId){
        return ResponseEntity.ok(utilisateurService.deleteUser(utilisateurId));
    }

}
