package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.TeamView.class, CustomJsonView.UtilisateurView.class, CustomJsonView.AdmnView.class})
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

    //A team can have multiple techs/admins as members but a tech/admin can only be a member of a single team
    @JsonView(CustomJsonView.TeamView.class)
    @OneToMany(mappedBy = "team")
    private Set<Admn> admins;


    /////////////////////
    //Getters & Setters//
    /////////////////////


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
