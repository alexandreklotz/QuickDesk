package fr.alexandreklotz.quickdesklite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.model.TicketQueue;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketQueueRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TicketQueueController {

    private TicketRepository ticketRepository;
    private TicketQueueRepository ticketQueueRepository;
    private TeamRepository teamRepository;

    @Autowired
    TicketQueueController(TicketRepository ticketRepository, TicketQueueRepository ticketQueueRepository, TeamRepository teamRepository){
        this.ticketRepository = ticketRepository;
        this.ticketQueueRepository = ticketQueueRepository;
        this.teamRepository = teamRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.TicketQueueView.class)
    @GetMapping("/admin/ticketqueue/all")
    public ResponseEntity<List<TicketQueue>> getAllQueues(){
        return ResponseEntity.ok(ticketQueueRepository.findAll());
    }

    @JsonView(CustomJsonView.TicketQueueView.class)
    @GetMapping("/admin/ticketqueue/{queueid}")
    public ResponseEntity<TicketQueue> getSpecifiedQueue(@PathVariable Long queueid){

        Optional<TicketQueue> ticketQueueBdd = ticketQueueRepository.findById(queueid);
        if(ticketQueueBdd.isPresent()){
            return ResponseEntity.ok(ticketQueueBdd.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
