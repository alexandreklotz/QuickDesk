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
public class TicketStatus {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.TicketStatusView.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketStatusView.class)
    private String name;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketStatusView.class)
    private boolean isDefault;

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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
