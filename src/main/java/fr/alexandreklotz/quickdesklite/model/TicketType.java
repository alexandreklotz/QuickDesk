package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CustomJsonView.TicketTypeView.class)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketTypeView.class)
    private String name;

    ///////////////
    //Constructor//
    ///////////////

    public TicketType(){}

    /////////////
    //Relations//
    /////////////

    //One type per ticket but multiple tickets per type
    @OneToMany(mappedBy = "ticketType")
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
