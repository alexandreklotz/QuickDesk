package fr.alexandreklotz.quickdesk.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.backend.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketType {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.TicketTypeView.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketTypeView.class)
    private String name;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketTypeView.class)
    private boolean isDefault;

    ///////////////
    //Constructor//
    ///////////////

    public TicketType(){}

    /////////////
    //Relations//
    /////////////

    //One type per ticket but multiple tickets per type
    @JsonIgnore
    @OneToMany(mappedBy = "ticketType")
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
