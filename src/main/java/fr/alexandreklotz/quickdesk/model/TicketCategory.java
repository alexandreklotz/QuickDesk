package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TicketCategory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.TicketCategoryView.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.TicketCategoryView.class, CustomJsonView.TicketView.class})
    private String name;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TicketCategoryView.class)
    private boolean isDefault;

    ///////////////
    //Constructor//
    ///////////////

    public TicketCategory(){}

    /////////////
    //Relations//
    /////////////

    //One category per ticket but mutliple tickets can be categorized identically
    @JsonView(CustomJsonView.TicketCategoryView.class)
    @OneToMany(mappedBy = "ticketCategory")
    private Set<Ticket> tickets;

    ///////////////////////
    //Getters and setters//
    ///////////////////////

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
