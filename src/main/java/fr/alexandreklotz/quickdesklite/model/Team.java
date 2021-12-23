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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.TeamView.class, CustomJsonView.UtilisateurView.class})
    private String teamName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private String teamDesc;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    @JsonView(CustomJsonView.TeamView.class)
    private Date teamDateCreated;

    /////////////
    //Relations//
    /////////////

    //A team can have multiple users but a user can only be in one team
    @JsonView(CustomJsonView.TeamView.class)
    @OneToMany(mappedBy = "team")
    private List<Utilisateur> utilisateurs;



}
