package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Permission {

    //TODO : From @Entity to @Embeddable ? Need to take into consideration that a role has multiple permissions, therefore a list or a set might be needed in role

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

}
