package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonView(CustomJsonView.PermissionView.class)
    private int permissionId;

    @JsonView(CustomJsonView.PermissionView.class)
    @Column(nullable = false)
    private String permissionName;

    @JsonView(CustomJsonView.PermissionView.class)
    @Column(nullable = false)
    private String permissionDesc;

    /////////////
    //Relations//
    /////////////

    //A role can have multiple permissions and a permission can have multiple roles
    @JsonView(CustomJsonView.PermissionView.class)
    @ManyToMany(mappedBy = "rolePermissions")
    private Set<Role> permissionsRoles = new HashSet<>();

    ///////////////
    //Constructor//
    ///////////////

    public Permission(){}

    ///////////////////////
    //Getters and setters//
    ///////////////////////


    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public Set<Role> getPermissionsRoles() {
        return permissionsRoles;
    }

    public void setPermissionsRoles(Set<Role> permissionsRoles) {
        this.permissionsRoles = permissionsRoles;
    }
}
