package fr.alexandreklotz.quickdesk.service;

import fr.alexandreklotz.quickdesk.error.RolesException;
import fr.alexandreklotz.quickdesk.model.Roles;

import java.util.List;

public interface RolesService {

    public List<Roles> getAllRoles();

    public Roles getRoleByName(String roleName) throws RolesException;

    public Roles getRoleById(Long roleId) throws RolesException;
}
