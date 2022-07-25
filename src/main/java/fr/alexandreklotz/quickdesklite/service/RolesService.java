package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.error.RolesException;
import fr.alexandreklotz.quickdesklite.model.Roles;

import java.util.List;
import java.util.UUID;

public interface RolesService {

    public List<Roles> getAllRoles();

    public Roles getRoleByName(String roleName) throws RolesException;

    public Roles getRoleById(Long roleId) throws RolesException;
}
