package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.RoleView.class)
    private int roleId;

    @Column(nullable = false)
    @JsonView(CustomJsonView.RoleView.class)
    private String roleName;

    @Column(nullable = false)
    @JsonView(CustomJsonView.RoleView.class)
    private String roleDescription;

    @Column(nullable = false)
    @JsonView(CustomJsonView.RoleView.class)
    private boolean roleCanBeDeleted;

    /////////////
    //Relations//
    /////////////

    //A role can have multiple permissions and a permission can have multiple roles
    @JsonView(CustomJsonView.RoleView.class)
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "rolePermissions",
            joinColumns = { @JoinColumn(name = "roleId")},
            inverseJoinColumns = {@JoinColumn(name = "permissionId")}
    )
    Set<Ticket> rolePermissions = new HashSet<>();

    //A role can be assigned to multiple groups but a group can only be affected a single group
    @JsonView(CustomJsonView.RoleView.class)
    @OneToMany(mappedBy = "role")
    private Set<Team> teams;

    ///////////////
    //Constructor//
    ///////////////

    public Role(){}

    ///////////////////////
    //Setters and getters//
    ///////////////////////

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public boolean isRoleCanBeDeleted() {
        return roleCanBeDeleted;
    }

    public void setRoleCanBeDeleted(boolean roleCanBeDeleted) {
        this.roleCanBeDeleted = roleCanBeDeleted;
    }

    public Set<Ticket> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Set<Ticket> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
