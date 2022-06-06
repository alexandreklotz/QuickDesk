package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.service.implementation.UtilisateurServiceImpl;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurServiceImpl utilisateurServiceImpl;

    @Autowired
    UtilisateurController (UtilisateurServiceImpl utilisateurServiceImpl){
        this.utilisateurServiceImpl = utilisateurServiceImpl;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/all")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(){
        return ResponseEntity.ok(utilisateurServiceImpl.getAllUsers());
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/{utilisateurid}")
    public ResponseEntity<Utilisateur> getSpecifiedUtilisateur(@PathVariable UUID utilisateurid){
        return ResponseEntity.ok(utilisateurServiceImpl.getUserById(utilisateurid));
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/{login}")
    public ResponseEntity<Utilisateur> getSpecifiedUserWithLogin(@PathVariable String login){
        return ResponseEntity.ok(utilisateurServiceImpl.getUserByLogin(login));
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/admin/utilisateur/new")
    public ResponseEntity<Utilisateur> newUtilisateur(@RequestBody Utilisateur utilisateur){
        return ResponseEntity.ok(utilisateurServiceImpl.createUser(utilisateur));
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("/admin/utilisateur/update/{utilisateurId}")
    public ResponseEntity<Utilisateur> updateUtilisateur (@RequestBody Utilisateur utilisateur){
        return ResponseEntity.ok(utilisateurServiceImpl.updateUser(utilisateur));
    }

    //TODO : Fix the delete method.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @DeleteMapping("/admin/utilisateur/{utilisateurId}/delete/")
    public ResponseEntity deleteUtilisateur (@RequestBody Utilisateur utilisateur){
        utilisateurServiceImpl.deleteUser(utilisateur);
        return ResponseEntity.ok().build();
    }

}
