package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.UserView.class)
    private int userId;

    @JsonView({CustomJsonView.UserView.class, CustomJsonView.TeamView.class})
    @Column(nullable = false)
    private String userFirstName;

    @JsonView({CustomJsonView.UserView.class, CustomJsonView.TeamView.class})
    @Column(nullable = false)
    private String userLastName;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    private String userPassword;

    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    private Date userCreationDate;

    //TODO : See if it will still be useful once a UserDetailsConfig will be implemented in the security package. Will probably need to be deleted.
    @JsonView(CustomJsonView.UserView.class)
    @Column(nullable = false)
    private boolean userEnabled;

    /////////////
    //Relations//
    /////////////

    //A user can only be a member of a single group
    @JsonView(CustomJsonView.UserView.class)
    @ManyToOne
    @JoinColumn(name="teamId", nullable = false)
    private Team team;

    //A user can create multiple tickets and a ticket can have multiple affected users
    @JsonView(CustomJsonView.UserView.class)
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "userTickets",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "ticketId") }
    )
    Set<Ticket> ticketsUsr = new HashSet<>();

    //A user can use multiple devices but a device can only be used by one user at a time
    @JsonView(CustomJsonView.UserView.class)
    @OneToMany(mappedBy = "user")
    private Set<Device> device;

}
