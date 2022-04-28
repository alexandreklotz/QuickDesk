package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketStatusView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketStatusView.class)
    private String name;

    ///////////////
    //Constructor//
    ///////////////

    public TicketStatus(){}

    /////////////
    //Relations//
    /////////////

    //One status per ticket but multiple tickets can have the same status
    @OneToMany(mappedBy = "ticketStatus")
    private Set<Ticket> tickets;

    /////////////////////
    //Getters & setters//
    /////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
