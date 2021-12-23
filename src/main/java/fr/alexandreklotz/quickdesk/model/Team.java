package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonView({CustomJsonView.TeamView.class, CustomJsonView.UserView.class})
    private int teamId;

    @Column(nullable = false)
    @JsonView({CustomJsonView.TeamView.class, CustomJsonView.UserView.class, CustomJsonView.DeviceView.class, CustomJsonView.TicketView.class})
    private String teamName;

    @Column
    @JsonView(CustomJsonView.TeamView.class)
    private String teamDescription;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    private Date teamDateCreated;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private boolean teamEnabled;

    /////////////
    //Relations//
    /////////////

    //A team has multiple members but a user can only be in one team.
    @JsonView(CustomJsonView.TeamView.class)
    @OneToMany(mappedBy = "team")
    private List<User> user;

    //A team can create multiple tickets and a ticket can have oneaffected team
    @JsonView(CustomJsonView.TeamView.class)
    /*@ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "teamTickets",
            joinColumns = { @JoinColumn(name = "teamId") },
            inverseJoinColumns = { @JoinColumn(name = "ticketId") }
    )
    private Set<Ticket> ticket;*/

    @OneToMany(mappedBy = "team")
    private List<Ticket> tickets;

}
