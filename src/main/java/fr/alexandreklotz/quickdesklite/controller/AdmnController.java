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

@RestController
@CrossOrigin
public class AdmnController {

    private AdmnRepository admnRepository;
    private TicketRepository ticketRepository;
    private TeamRepository teamRepository;

    @Autowired
    AdmnController(AdmnRepository admnRepository, TicketRepository ticketRepository, TeamRepository teamRepository){
        this.admnRepository = admnRepository;
        this.ticketRepository = ticketRepository;
        this.teamRepository = teamRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.AdmnView.class)
    @GetMapping("/admin/administrators/all")
    public ResponseEntity<List<Admn>> getAllAdmins(){
        return ResponseEntity.ok(admnRepository.findAll());
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @GetMapping("/admin/administrators/{admnid}")
    public ResponseEntity<Admn> getSpecifiedAdmin (@PathVariable Long admnid) {
        Optional<Admn> admnBdd = admnRepository.findById(admnid);
        if(admnBdd.isPresent()){
            return ResponseEntity.ok(admnBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @PostMapping("/admin/administrators/new")
    public ResponseEntity<String> newAdmin (@RequestBody Admn admn){

        Optional<Team> teamBdd = teamRepository.findById(admn.getTeam().getId());
        if(teamBdd.isPresent()){
            admn.setTeam(teamBdd.get());
        }
        if(admn.getAdmnType() == null){
            admn.setAdmnType(Admn.AdmnType.ADMIN);
        }

        admn.setCreationDate(LocalDateTime.now());

        admnRepository.saveAndFlush(admn);
        return ResponseEntity.ok(admn.getAdmnLogin() + " has been successfully created with the following role : " + admn.getAdmnType());
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @PutMapping("/admin/administrators/edit/{admnid}")
    public ResponseEntity<String> modifyAdmin (@PathVariable Long admnid,
                                               @RequestBody Admn admn){

        Optional<Admn> admnBdd = admnRepository.findById(admnid);
        if(admnBdd.isPresent()){
            if(admn.getAdmnType() != null){
                admnBdd.get().setAdmnType(Admn.AdmnType.ADMIN);
                //This is the only role an admn can have (admn is the abbreviation for admin). I'm wondering if the Type is required since this class only has this role.
                //I need to see how it works once i'll start implementing security. If i can manage the access to specific methods only to admins by using an object's
                //entity then the type will be deleted.
            }
            if(admn.getAdmnLogin() != null){
                admnBdd.get().setAdmnLogin(admn.getAdmnLogin());
            }
            if(admn.getAdmnFirstName() != null){
                admnBdd.get().setAdmnFirstName(admn.getAdmnFirstName());
            }
            if(admn.getAdmnLastName() != null){
                admnBdd.get().setAdmnLastName(admn.getAdmnLastName());
            }
            if(admn.getAdmnMailaddr() != null){
                admnBdd.get().setAdmnMailaddr(admn.getAdmnMailaddr());
            }
            if(admn.getAdmnPwd() != null){
                admnBdd.get().setAdmnPwd(admn.getAdmnPwd());
            }
            admnRepository.save(admnBdd.get());
        } else {
            return ResponseEntity.badRequest().body("The admin you're trying to modify doesn't exist.");
        }
        return ResponseEntity.ok(admnBdd.get().getAdmnLogin() + " has been successfully updated.");
    }

    @JsonView(CustomJsonView.AdmnView.class)
    @DeleteMapping("/admin/administrators/{admnid}/delete/")
    public String deleteAdmin (@PathVariable Long admnid){

        Optional<Admn> admnBdd = admnRepository.findById(admnid);
        if(admnBdd.isPresent()){
            String delAdm = admnBdd.get().getAdmnLogin() + " has been successfully deleted.";
            admnRepository.deleteById(admnid);
            return delAdm;
        } else {
            return "The admin you're trying to delete doesn't exist.";
        }
    }
}
