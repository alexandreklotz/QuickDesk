package fr.alexandreklotz.quickdesk.backend.service;

import fr.alexandreklotz.quickdesk.backend.error.RolesException;
import fr.alexandreklotz.quickdesk.backend.model.Roles;

import java.util.List;

public interface RolesService {

    public List<Roles> getAllRoles();

    public Roles getRoleByName(String roleName) throws RolesException;

    public Roles getRoleById(Long roleId) throws RolesException;
}
