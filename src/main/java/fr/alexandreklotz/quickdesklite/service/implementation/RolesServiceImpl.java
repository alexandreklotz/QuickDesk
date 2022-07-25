package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.RolesException;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesRepository rolesRepository;

    @Autowired
    RolesServiceImpl(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }


    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles getRoleByName(String roleName) throws RolesException {
        Optional<Roles> role = rolesRepository.findRoleWithName(roleName);
        if(!role.isPresent()){
            throw new RolesException("The role " + roleName + " doesn't exist");
        }
        return role.get();
    }

    @Override
    public Roles getRoleById(Long roleId) throws RolesException {
        Optional<Roles> roleBdd = rolesRepository.findById(roleId);
        if(!roleBdd.isPresent()){
            throw new RolesException("The role with the id " + roleId + " doesn't exist.");
        }
        return roleBdd.get();
    }
}
