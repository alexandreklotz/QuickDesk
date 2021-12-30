package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Utilisateur {

    public enum UserType {
        USER,
        TECHNICIAN,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    private String utilFirstName;

    @Column(nullable = false)
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.TeamView.class, CustomJsonView.TicketView.class})
    private String utilLastName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String utilLogin;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String utilPwd;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    @JsonView(CustomJsonView.UtilisateurView.class)
    private Date creationDate;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private UserType userType;

    /////////////
    //Relations//
    /////////////

    //A user can be part of only one team but a team can have multiple users
    @JsonView(CustomJsonView.UtilisateurView.class)
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    //A user can create multiple tickets but a ticket can only be assigned to one user
    @JsonView(CustomJsonView.UtilisateurView.class)
    @OneToMany(mappedBy = "utilisateur")
    private List<Ticket> tickets;

    //A user, whatever is role is, can create multiple comments but a comment can only be created/linked by/to one user.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @OneToMany(mappedBy = "utilisateur")
    private List<Comment> comments;

}
