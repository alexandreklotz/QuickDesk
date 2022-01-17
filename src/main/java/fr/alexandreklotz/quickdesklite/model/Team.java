package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    @JsonView(CustomJsonView.TeamView.class)
    private UUID id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.TeamView.class, CustomJsonView.UtilisateurView.class})
    private String teamName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private String teamDesc;

    @JsonView(CustomJsonView.TeamView.class)
    @Column(nullable = false)
    private LocalDateTime teamDateCreated;

    ///////////////
    //Constructor//
    ///////////////

    public Team(){}

    /////////////
    //Relations//
    /////////////

    //A team can have multiple users but a user can only be in one team
    @JsonView(CustomJsonView.TeamView.class)
    @OneToMany(mappedBy = "team")
    private Set<Utilisateur> utilisateurs;

    //A team can have multiple admins/techs but a tech/admin can only be in one team
    @JsonView(CustomJsonView.TeamView.class)
    @OneToMany(mappedBy = "team")
    private Set<Admn> admins;


    /////////////////////
    //Getters & Setters//
    /////////////////////


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public LocalDateTime getTeamDateCreated() {
        return teamDateCreated;
    }

    public void setTeamDateCreated(LocalDateTime teamDateCreated) {
        this.teamDateCreated = teamDateCreated;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Set<Admn> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admn> admins) {
        this.admins = admins;
    }
}
