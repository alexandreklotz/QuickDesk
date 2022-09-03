package fr.alexandreklotz.quickdesk.controller.admin;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.quickdesk.error.RolesException;
import fr.alexandreklotz.quickdesk.model.Roles;
import fr.alexandreklotz.quickdesk.service.RolesService;
import fr.alexandreklotz.quickdesk.view.CustomJsonView;
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

    @JsonView(CustomJsonView.RolesView.class)
    @GetMapping("/admin/roles/all")
    public List<Roles> getAllRoles(){
        return rolesService.getAllRoles();
    }

    @JsonView(CustomJsonView.RolesView.class)
    @GetMapping("/admin/roles/name/{roleName}")
    public Roles getRoleByName(@PathVariable String roleName) throws RolesException {
        return rolesService.getRoleByName(roleName);
    }

    @JsonView(CustomJsonView.RolesView.class)
    @GetMapping("/admin/roles/id/{roleId}")
    public Roles getRoleById(@PathVariable Long roleId) throws RolesException{
        return rolesService.getRoleById(roleId);
    }
}
