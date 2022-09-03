package fr.alexandreklotz.quickdesk.service.implementation;

import fr.alexandreklotz.quickdesk.error.RolesException;
import fr.alexandreklotz.quickdesk.model.Roles;
import fr.alexandreklotz.quickdesk.repository.RolesRepository;
import fr.alexandreklotz.quickdesk.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if(role.isEmpty()){
            throw new RolesException("ERROR : The role " + roleName + " doesn't exist");
        }
        return role.get();
    }

    @Override
    public Roles getRoleById(Long roleId) throws RolesException {
        Optional<Roles> roleBdd = rolesRepository.findById(roleId);
        if(roleBdd.isEmpty()){
            throw new RolesException("ERROR : The role with the id " + roleId + " doesn't exist.");
        }
        return roleBdd.get();
    }
}
