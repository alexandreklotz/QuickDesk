package fr.alexandreklotz.quickdesklite.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesklite.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.RolesView.class)
    private Long id;

    @Column(nullable = false)
    @JsonView({CustomJsonView.RolesView.class, CustomJsonView.UtilisateurView.class})
    private String roleName;

    ///////////////
    //Constructor//
    ///////////////

    public Roles(){}

    /////////////
    //Relations//
    /////////////

    //A user has only one role but the same role can be assigned to multiple users
    @JsonView(CustomJsonView.RolesView.class)
    @OneToMany(mappedBy = "role")
    private Set<Utilisateur> utilisateurs;

    /////////////////////
    //Getters & setters//
    /////////////////////


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public String toString(){
        return this.roleName;
    }

}
