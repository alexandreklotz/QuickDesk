package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
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
    @JsonView({CustomJsonView.TeamView.class, CustomJsonView.UtilisateurView.class})
    private String teamName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.TeamView.class)
    private String teamDesc;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd-YYYY")
    @JsonView(CustomJsonView.TeamView.class)
    private Date teamDateCreated;

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

    public Date getTeamDateCreated() {
        return teamDateCreated;
    }

    public void setTeamDateCreated(Date teamDateCreated) {
        this.teamDateCreated = teamDateCreated;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
