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

    @JsonView(CustomJsonView.TeamView.class)
    @Column(nullable = false)
    private boolean techTeam;

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

    //A team can access multiple queues and vice versa
    @JsonView(CustomJsonView.TeamView.class)
    @ManyToMany(mappedBy = "teams")
    private Set<TicketQueue> ticketqueue;

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

    public boolean isTechTeam() {
        return techTeam;
    }

    public void setTechTeam(boolean techTeam) {
        this.techTeam = techTeam;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Set<TicketQueue> getTicketqueue() {
        return ticketqueue;
    }

    public void setTicketqueue(Set<TicketQueue> ticketqueue) {
        this.ticketqueue = ticketqueue;
    }

    @Override
    public String toString(){
        return this.teamName;
    }
}
