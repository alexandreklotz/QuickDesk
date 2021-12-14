package fr.alexandreklotz.quickdesk.model;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private List<Permission> rolePermissions;

    //A role can be assigned to multiple groups but a group can only be affected a single group
    @JsonView(CustomJsonView.RoleView.class)
    @OneToMany(mappedBy = "role")
    private List<Team> teams;

}
