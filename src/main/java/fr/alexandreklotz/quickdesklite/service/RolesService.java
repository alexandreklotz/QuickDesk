package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RolesService {

    private RolesRepository rolesRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    RolesService(RolesRepository rolesRepository, UtilisateurRepository utilisateurRepository){
        this.rolesRepository = rolesRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    //////////////////////////////////////////////////////////////////////////////////
    //Methods to retrieve all roles, specified role, users' list of a specified role//
    //////////////////////////////////////////////////////////////////////////////////

    //Retrieve all the roles
    public List<Roles> getAllRoles(){
        return rolesRepository.findAll();
    }

    //Retrieve a specific role
    public Roles getSpecifiedRole(String name){
        Optional<Roles> searchedRole = rolesRepository.findRoleWithName(name);
        if(searchedRole.isPresent()){
            return searchedRole.get();
        } else {
            return null;
        }
    }

    //Retrieve users from specified role
    public Set<Utilisateur> getRoleUsers(String name){
        Optional<Roles> role = rolesRepository.findRoleWithName(name);
        if(role.isPresent()){
            return role.get().getUtilisateurs();
        } else {
            return null;
        }
    }

}
