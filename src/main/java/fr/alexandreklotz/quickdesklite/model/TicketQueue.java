package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketQueue {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.TicketQueueView.class)
    private UUID id;

    @JsonView(CustomJsonView.TicketQueueView.class)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketQueueView.class)
    private boolean isDefault;

    ///////////////
    //Constructor//
    ///////////////

    public TicketQueue(){}

    /////////////
    //Relations//
    /////////////

    //A team can access multiple queues and vice versa
    @JsonView(CustomJsonView.TicketQueueView.class)
    @ManyToMany
    @JoinTable(
            name = "team_queues",
            joinColumns = @JoinColumn(name = "ticket_queue_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    //A queue can contain multiple tickets but a ticket can only be assigned to one queue
    @JsonView(CustomJsonView.TicketQueueView.class)
    @OneToMany(mappedBy = "ticketQueue")
    private Set<Ticket> tickets;

    /////////////////////
    //Getters & setters//
    /////////////////////


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
