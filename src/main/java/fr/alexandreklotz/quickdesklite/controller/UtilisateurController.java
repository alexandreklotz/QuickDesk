package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurRepository utilisateurRepository;
    private TeamRepository teamRepository;
    private TicketRepository ticketRepository;
    private PasswordEncoder passwordEncoder;
    private RolesRepository rolesRepository;

    @Autowired
    UtilisateurController (UtilisateurRepository utilisateurRepository, TeamRepository teamRepository, TicketRepository ticketRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository){
        this.ticketRepository = ticketRepository;
        this.teamRepository = teamRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/all")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(){
        return ResponseEntity.ok(utilisateurRepository.findAll());
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/admin/utilisateur/{utilisateurid}")
    public ResponseEntity<Utilisateur> getSpecifiedUtilisateur(@PathVariable UUID utilisateurid){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateurid);

        if(userBdd.isPresent()){
            return ResponseEntity.ok(userBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }



    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/admin/utilisateur/new")
    public ResponseEntity<String> newUtilisateur(@RequestBody Utilisateur utilisateur){

        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(utilisateur.getUtilLogin());
        if(userBdd.isPresent()){
            return ResponseEntity.badRequest().body("The specified login is already in use.");
        }

        Optional<Team> teamBdd = teamRepository.findById(utilisateur.getTeam().getId());
        if(teamBdd.isPresent()){
            utilisateur.setTeam(utilisateur.getTeam());
        }

        utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));
        utilisateur.setCreationDate(LocalDateTime.now());
        utilisateur.setUtilEnabled(true);


        if(utilisateur.getRole() == null){
            Roles roleBdd = rolesRepository.getById(3L);
            utilisateur.setRole(roleBdd);
        } else if (utilisateur.getRole() != null){
            Optional<Roles> roleBdd = rolesRepository.findById(utilisateur.getRole().getId());
            if(roleBdd.isPresent()){
                utilisateur.setRole(roleBdd.get());
            }
        }

        utilisateurRepository.saveAndFlush(utilisateur);
        return ResponseEntity.ok("User successfully created.");
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("/admin/utilisateur/update/{utilisateurId}")
    public ResponseEntity<String> updateUtilisateur (@PathVariable UUID utilisateurId, @RequestBody Utilisateur utilisateur){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateurId);
        if(userBdd.isPresent()){

            if (utilisateur.getRole() != null){
                Optional<Roles> roleBdd = rolesRepository.findById(utilisateur.getRole().getId());
                if(roleBdd.isPresent()){
                    utilisateur.setRole(roleBdd.get());
                }
            }

            if (utilisateur.getTeam() != null){
                Optional<Team> teamBdd = teamRepository.findById(utilisateur.getTeam().getId());
                if(teamBdd.isPresent()){
                    userBdd.get().setTeam(teamBdd.get());
                }
            }
            if(utilisateur.getUtilFirstName() != null){
                userBdd.get().setUtilFirstName(utilisateur.getUtilFirstName());
            }
            if(utilisateur.getUtilLastName() != null) {
                userBdd.get().setUtilLastName(utilisateur.getUtilLastName());
            }
            if(utilisateur.getUtilPwd() != null){
                userBdd.get().setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));
            }
            if(utilisateur.getUtilLogin() != null){
                userBdd.get().setUtilLogin(utilisateur.getUtilLogin());
            }
            utilisateurRepository.save(userBdd.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @DeleteMapping("/admin/utilisateur/delete/{utilisateurId}")
    public String deleteUtilisateur (@PathVariable UUID utilisateurId){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateurId);
        if(userBdd.isPresent()){
            String deletedUser = userBdd.get().getUtilFirstName() + " " + userBdd.get().getUtilLastName() + " which was in the " + userBdd.get().getTeam().getTeamName() + " team has been deleted.";
            utilisateurRepository.deleteById(utilisateurId);
            return deletedUser;
        } else {
            return "The specified user doesn't exist or an error occurred.";
        }
    }

}
