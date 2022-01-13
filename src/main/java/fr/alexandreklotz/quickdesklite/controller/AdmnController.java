package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.Admn;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.repository.AdmnRepository;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class AdmnController {

    private AdmnRepository admnRepository;
    private TeamRepository teamRepository;
    private TicketRepository ticketRepository;

    @Autowired
    AdmnController (AdmnRepository admnRepository, TeamRepository teamRepository, TicketRepository ticketRepository){
        this.admnRepository = admnRepository;
        this.teamRepository = teamRepository;
        this.ticketRepository = ticketRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.AdmnView.class)
    @GetMapping("/admin/adminstrateurs/all")
    public ResponseEntity<List<Admn>> getAllAdmins(){
        return ResponseEntity.ok(admnRepository.findAll());
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @GetMapping("/admin/administrateurs/{admnId}")
    public ResponseEntity<Admn> getSpecifiedAdmin (@PathVariable UUID admnId){

        Optional<Admn> admnBdd = admnRepository.findById(admnId);

        if(admnBdd.isPresent()){
            return ResponseEntity.ok(admnBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @JsonView(CustomJsonView.AdmnView.class)
    @PostMapping("/admin/administrateurs/new")
    public ResponseEntity<String> newAdmin (@RequestBody Admn admn){

        Optional<Team> teamBdd = teamRepository.findById(admn.getTeam().getId());
        if(teamBdd.isPresent()){
            admn.setTeam(teamBdd.get());
        }

        admn.setCreationDate(LocalDateTime.now());

        if(admn.getAdmnType() == null){
            return ResponseEntity.badRequest().body("You need to specifiy if you want to create a technician or an admin.");
        }

        admnRepository.saveAndFlush(admn);
        return ResponseEntity.ok(admn.getAdmnLogin() + " with the role : " + admn.getAdmnType() + " successfully created.");
    }


    @JsonView(CustomJsonView.AdmnView.class)
    @PutMapping("/admin/administrateurs/update/{admnId}")
    public ResponseEntity<String> updateAdmin (@PathVariable UUID admnId,
                                               @RequestBody Admn admn) {

        Optional<Admn> admnBdd = admnRepository.findById(admnId);

        if(admnBdd.isPresent()){

            if(admn.getAdmnPwd() != null) {
                admnBdd.get().setAdmnPwd(admn.getAdmnPwd());
            }
            if(admn.getAdmnFirstName() != null){
                admnBdd.get().setAdmnFirstName(admn.getAdmnFirstName());
            }
            if(admn.getAdmnLastName() != null){
                admnBdd.get().setAdmnLastName(admn.getAdmnLastName());
            }
            if(admn.getAdmnType() != null){
                admnBdd.get().setAdmnType(admn.getAdmnType());
            }
            if(admn.getAdmnLogin() != null){
                admnBdd.get().setAdmnLogin(admn.getAdmnLogin());
            }
            if(admn.getTeam() != null){
                Optional<Team> teamBdd = teamRepository.findById(admn.getTeam().getId());
                if(teamBdd.isPresent()){
                    admnBdd.get().setTeam(teamBdd.get());
                }
            }

            admnRepository.save(admnBdd.get());
            return ResponseEntity.ok(admnBdd.get().getAdmnLogin() + " has been successfully updated.");
        } else {
            return ResponseEntity.badRequest().body("Bad request or admin doesn't exist.");
        }
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @DeleteMapping("/admin/administrateurs/delete/{admnId}")
    public String deleteAdmin (@PathVariable UUID admnId) {
        Optional<Admn> admnBdd = admnRepository.findById(admnId);

        if(admnBdd.isPresent()){
            String delAdm = admnBdd.get().getAdmnLogin() + " has been succesfully deleted.";
            admnRepository.deleteById(admnId);
            return delAdm;
        } else {
            return "The admin you're trying to delete doesn't exist.";
        }
    }
}
