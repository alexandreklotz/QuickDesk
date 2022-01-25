package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class RolesController {

    private RolesRepository rolesRepository;
    private UtilisateurController utilisateurController;

    @Autowired
    RolesController(RolesRepository rolesRepository, UtilisateurController utilisateurController){
        this.rolesRepository = rolesRepository;
        this.utilisateurController = utilisateurController;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.RolesView.class)
    @GetMapping("/admin/roles/all")
    public ResponseEntity<List<Roles>> getAllRoles(){
        return ResponseEntity.ok(rolesRepository.findAll());
    }

    @JsonView(CustomJsonView.RolesView.class)
    @GetMapping("/admin/roles/{roleId}")
    public ResponseEntity<Roles> getSpecifiedRole (@PathVariable Long roleId){
        Optional<Roles> roleBdd = rolesRepository.findById(roleId);
        if(roleBdd.isPresent()){
            return ResponseEntity.ok(roleBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
