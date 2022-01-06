package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurRepository utilisateurRepository;
    private TeamRepository teamRepository;
    private TicketRepository ticketRepository;

    @Autowired
    UtilisateurController (UtilisateurRepository utilisateurRepository, TeamRepository teamRepository, TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
        this.teamRepository = teamRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/utilisateur/all")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs(){
        return ResponseEntity.ok(utilisateurRepository.findAll());
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/utilisateur/{utilisateurid}")
    public ResponseEntity<Utilisateur> getSpecifiedUtilisateur(@PathVariable Long utilisateurid){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(utilisateurid);

        if(userBdd.isPresent()){
            return ResponseEntity.ok(userBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/utilisateur/new")
    public ResponseEntity<String> newUtilisateur(@RequestBody Utilisateur utilisateur){

        Optional<Team> teamBdd = teamRepository.findById(utilisateur.getTeam().getId());
        if(teamBdd.isPresent()){
            utilisateur.setTeam(utilisateur.getTeam());
        }

        utilisateur.setCreationDate(LocalDateTime.now());

        if(utilisateur.getUserType() == null){
            utilisateur.setUserType(Utilisateur.UserType.USER);
        } else {
            utilisateur.setUserType(utilisateur.getUserType());
        }

        utilisateurRepository.saveAndFlush(utilisateur);
        return ResponseEntity.ok("User successfully created.");
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("/utilisateur/update/{userId}")
    public ResponseEntity<String> updateUtilisateur (@PathVariable Long userId,
                                                     @RequestBody Utilisateur utilisateur){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(userId);
        if(userBdd.isPresent()){
            if (utilisateur.getUserType() != null){
                userBdd.get().setUserType(utilisateur.getUserType());
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
                userBdd.get().setUtilPwd(utilisateur.getUtilPwd());
            }
            if(utilisateur.getUtilLogin() != null){
                userBdd.get().setUtilLogin(utilisateur.getUtilLogin());
            }
            if(utilisateur.getUtilMailaddr() != null){
                userBdd.get().setUtilMailaddr(utilisateur.getUtilMailaddr());
            }
            utilisateurRepository.save(userBdd.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @DeleteMapping("/utilisateur/delete/{userId}")
    public String deleteUtilisateur (@PathVariable Long userId){

        Optional<Utilisateur> userBdd = utilisateurRepository.findById(userId);
        if(userBdd.isPresent()){
            String deletedUser = userBdd.get().getUtilFirstName() + " " + userBdd.get().getUtilLastName() + " which was in the " + userBdd.get().getTeam().getTeamName() + " team has been deleted.";
            utilisateurRepository.deleteById(userId);
            return deletedUser;
        } else {
            return "The specified user doesn't exist or an error occurred.";
        }
    }

}
