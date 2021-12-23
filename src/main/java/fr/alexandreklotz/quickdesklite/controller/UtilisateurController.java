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

import java.time.Instant;
import java.util.Date;
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
    @PostMapping("/utilisateur/new")
    public void newUtilisateur(@RequestBody Utilisateur utilisateur){

        Optional<Team> teamBdd = teamRepository.findById(utilisateur.getTeam().getId());
        if(teamBdd.isPresent()){
            utilisateur.setTeam(utilisateur.getTeam());
        }

        utilisateur.setCreationDate(Date.from(Instant.now()));

        if(utilisateur.getUserType() == null){
            utilisateur.setUserType(Utilisateur.UserType.USER);
        } else {
            utilisateur.setUserType(utilisateur.getUserType());
        }

        utilisateurRepository.saveAndFlush(utilisateur);
    }

}
