package fr.alexandreklotz.quickdesk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.dao.PermissionDao;
import fr.alexandreklotz.quickdesk.dao.RoleDao;
import fr.alexandreklotz.quickdesk.dao.TeamDao;
import fr.alexandreklotz.quickdesk.model.Permission;
import fr.alexandreklotz.quickdesk.model.Role;
import fr.alexandreklotz.quickdesk.model.Team;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
public class RoleController {

    private RoleDao roleDao;
    private PermissionDao permissionDao;
    private TeamDao teamDao;

    @Autowired
    RoleController (RoleDao roleDao, PermissionDao permissionDao, TeamDao teamDao) {
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
        this.teamDao = teamDao;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @JsonView(CustomJsonView.RoleView.class)
    @GetMapping("/role/all")
    public ResponseEntity<List<Role>> getAllRoles () {
        return ResponseEntity.ok(roleDao.findAll());
    }



    @JsonView(CustomJsonView.RoleView.class)
    @PostMapping("/role/new")
    public void newRole (@RequestBody Role role) {

        role.setRoleCanBeDeleted(false);

        Set<Permission> rolePerm = role.getRolePermissions();
        for(Permission permission : rolePerm) {
            Optional<Permission> permissionBdd = permissionDao.findById(permission.getPermissionId());
            if(permissionBdd.isPresent()) {
                role.setRolePermissions(role.getRolePermissions());
            }
        }

        Set<Team> roleTeam = role.getTeams();
        for (Team team : roleTeam) {
            Optional<Team> teamBdd = teamDao.findById(team.getTeamId());
            if(teamBdd.isPresent()) {
                role.setTeams(role.getTeams());
            }
        }


    }

    @JsonView(CustomJsonView.RoleView.class)
    @DeleteMapping("/role/delete/{roleId}")
    public String deleteRole (@PathVariable int roleId) {

        Optional<Role> roleBdd = roleDao.findById(roleId);
        if(roleBdd.isPresent()){
            String roleDeleted = roleBdd.get().getRoleName();
            roleDao.deleteById(roleId);
            return "The Role " + roleDeleted + " has been deleted.";
        }
        return "The specified role doesn't exist.";
    }

}
