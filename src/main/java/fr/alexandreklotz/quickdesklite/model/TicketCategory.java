package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CustomJsonView.TicketCategoryView.class)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketCategoryView.class)
    private String name;

    ///////////////
    //Constructor//
    ///////////////

    public TicketCategory(){}

    /////////////
    //Relations//
    /////////////

    //One category per ticket but mutliple tickets can be categorized identically
    @OneToMany(mappedBy = "ticketCategory")
    private Set<Ticket> tickets;

    ///////////////////////
    //Getters and setters//
    ///////////////////////

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
