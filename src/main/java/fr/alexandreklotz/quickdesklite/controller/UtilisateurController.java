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

        //Before processing the creation request, we verify if another user already uses the login specified in this request.
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(utilisateur.getUtilLogin());
        if(userBdd.isPresent()){
            return ResponseEntity.badRequest().body("The specified login is already in use.");
        }

        //We verify that the specified team exists before assigning it to the user.
        //If there isn't any team specified, we leave the field blank.
        if(utilisateur.getTeam() != null){
            Optional<Team> teamBdd = teamRepository.findById(utilisateur.getTeam().getId());
            if(teamBdd.isPresent()){
                utilisateur.setTeam(utilisateur.getTeam());
            } else {
                return ResponseEntity.badRequest().body("The specified team doesn't exist.");
            }
        } else if (utilisateur.getTeam() == null) {
            utilisateur.setTeam(null);
        }


        utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd())); //TODO : passwordEncoder is NOT OK !! Should use the BCrypt bean...
        utilisateur.setCreationDate(LocalDateTime.now());
        utilisateur.setUtilEnabled(true);


        //If the new user's role is null, we automatically give him the USER role.
        if(utilisateur.getRole() == null){
            Roles roleBdd = rolesRepository.getById(3L);
            utilisateur.setRole(roleBdd);
        } else if (utilisateur.getRole() != null){
            //If it isn't null, we check that the specified role exists before assigning it to the user.
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

        //We verify that the user exists before proceeding with the update process.
        Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateurId);
        if(userBdd.isPresent()){

            if (utilisateur.getRole() != null){
                Optional<Roles> roleBdd = rolesRepository.findById(utilisateur.getRole().getId());
                if(roleBdd.isPresent()){
                    userBdd.get().setRole(roleBdd.get());
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
