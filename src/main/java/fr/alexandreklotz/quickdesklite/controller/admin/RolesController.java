package fr.alexandreklotz.quickdesklite.controller.admin;

import fr.alexandreklotz.quickdesklite.error.RolesException;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class RolesController {

    private RolesService rolesService;

    @Autowired
    RolesController(RolesService rolesService){
        this.rolesService = rolesService;
    }

    @GetMapping("/admin/roles/all")
    public List<Roles> getAllRoles(){
        return rolesService.getAllRoles();
    }

    @GetMapping("/admin/roles/{roleName}")
    public Roles getRoleByName(@PathVariable String roleName) throws RolesException {
        return rolesService.getRoleByName(roleName);
    }

    @GetMapping("/admin/roles/{roleId}")
    public Roles getRoleById(@PathVariable Long roleId) throws RolesException{
        return rolesService.getRoleById(roleId);
    }
}
