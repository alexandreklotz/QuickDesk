package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;
    private DeviceRepository deviceRepository;
    private PasswordEncoder passwordEncoder;
    private TeamRepository teamRepository;

    @Autowired
    UtilisateurService(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository, DeviceRepository deviceRepository, PasswordEncoder passwordEncoder, TeamRepository teamRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
        this.deviceRepository = deviceRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamRepository = teamRepository;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //This service will be used to retrieve the users' info such as roles, name, etc...//
    //It will also be used to create, delete, update and retrieve users                //
    /////////////////////////////////////////////////////////////////////////////////////

    //This method is used to verify that the specified user exists and if so it returns a boolean
    public boolean isUserExisting(String login){
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserAdmin(String login){
        String roleName = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            Optional<Roles> roleBdd = rolesRepository.findById(userBdd.get().getRole().getId());
            if(roleBdd.isPresent()){
                roleName = roleBdd.get().getRoleName();
            }
        }
        if(roleName.equals("ADMIN")){
            return true;
        } else {
            return false;
        }
    }

    public String getUserFullName (String login) {
        String userName = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            userName = userBdd.get().toString();
        }
        return userName;
    }

    public String getUserDevice (String login) {
        String deviceName = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            Optional<Device> deviceBdd = deviceRepository.findById(userBdd.get().getDevice().getId());
            if(deviceBdd.isPresent()){
                deviceName = deviceBdd.get().getDeviceName();
            } else {
                deviceName = "NoDevice";
            }
        }
        return deviceName;
    }

    ////////
    //CRUD//
    ////////

    //5 methods, create user, delete user, update user, retrieve all users, retrieve requested user, update user role

    //Method to retrieve all users.
    public List<Utilisateur> getAllUsers(){
        return utilisateurRepository.findAll();
    }

    //Method to retrieve a specific user with hid ID
    public Utilisateur getUserById(UUID userid){
        Optional<Utilisateur> searchedUser = utilisateurRepository.findById(userid);
        if(searchedUser.isPresent()){
            return searchedUser.get();
        } else {
            return null;
        }
    }

    //Method to retrieve a specific user with his login
    public Utilisateur getUserByLogin(String login){
        Optional<Utilisateur> searchedUser = utilisateurRepository.findUserWithLogin(login);
        if(searchedUser.isPresent()){
            return searchedUser.get();
        } else {
            return null;
        }
    }

    //Method to create a user
    public String createUser(Utilisateur utilisateur){

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());

        if(existingUser){
            return "This login is already assigned to an existing user.";
        } else {

            utilisateur.setUtilEnabled(true);
            utilisateur.setCreationDate(LocalDateTime.now());

            if(utilisateur.getUtilPwd() != null) {
                utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));
            } else {
                return "Password cannot be empty.";
            }

            if(utilisateur.getRole() == null){
                Roles roleBdd = rolesRepository.getById(3L);
                utilisateur.setRole(roleBdd);
            } else if (utilisateur.getRole() != null){
                Optional<Roles> roleBdd = rolesRepository.findById(utilisateur.getRole().getId());
                if(roleBdd.isPresent()){
                    utilisateur.setRole(roleBdd.get());
                } else {
                    return "You must assign a role to the user !"; //return error
                }
                utilisateurRepository.saveAndFlush(utilisateur);
            }
        }
        return "User has been successfully created.";
    }

    //Method to update a user's role
    public String updateUserRole(Utilisateur utilisateur){
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        Roles currentRole = rolesRepository.getById(updatedUser.get().getRole().getId());
        if(updatedUser.isPresent()){
            Optional<Roles> newRole = rolesRepository.findById(utilisateur.getRole().getId());
            if(newRole.isPresent()){
                updatedUser.get().setRole(newRole.get());
                utilisateurRepository.saveAndFlush(updatedUser.get());
                return "The user's role has been updated.";
            } else {
                updatedUser.get().setRole(currentRole);
                utilisateurRepository.saveAndFlush(updatedUser.get());
                return "The specified role doesn't exist, role hasn't been updated.";
            }
        }
        return "Couldn't update the user's role.";
    }

    //Method to update a user's team
    public String updateUserTeam(Utilisateur utilisateur){
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isPresent()){
            Team currentTeam = teamRepository.getById(updatedUser.get().getTeam().getId());
            Optional<Team> team = teamRepository.findById(utilisateur.getTeam().getId());
            if(team.isPresent()){
                updatedUser.get().setTeam(team.get());
                utilisateurRepository.saveAndFlush(updatedUser.get());
                return "User's team updated.";
            } else {
                updatedUser.get().setTeam(currentTeam);
                utilisateurRepository.saveAndFlush(updatedUser.get());
                return "The specified team doesn't exist, team hasn't been updated.";
            }
        }
        return "Couldn't update the user's team";
    }

    //Method to update an existing user
    public String updateUser(Utilisateur utilisateur){
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isPresent()){

            if(utilisateur.getRole() != null){
                Optional<Roles> newRole = rolesRepository.findById(utilisateur.getRole().getId());
                if(newRole.isPresent()){
                    updatedUser.get().setRole(newRole.get());
                } else {
                    return "The specified role doesn't exist.";
                }
            }
            if(utilisateur.getTeam() != null){
                Optional<Team> newTeam = teamRepository.findById(utilisateur.getTeam().getId());
                if(newTeam.isPresent()){
                    updatedUser.get().setTeam(newTeam.get());
                } else {
                    return "The specified team doesn't exist.";
                }
            }
            if(utilisateur.getUtilFirstName() != null){
                updatedUser.get().setUtilFirstName(utilisateur.getUtilFirstName());
            }
            if(utilisateur.getUtilLastName() != null){
                updatedUser.get().setUtilLastName(utilisateur.getUtilLastName());
            }
            if(utilisateur.getUtilLogin() != null){
                boolean existingUser = isUserExisting(utilisateur.getUtilLogin());
                if(existingUser){
                    return "The specified login is already assigned to another user.";
                } else {
                    updatedUser.get().setUtilLogin(utilisateur.getUtilLogin());
                }
            }
            if(utilisateur.getUtilPwd() != null){
                updatedUser.get().setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));
            }

            utilisateurRepository.saveAndFlush(updatedUser.get());
            //return "The user has been successfully updated.";

        } else {
            return "The user hasn't been found or couldn't be updated.";
        }
        return "The specified user has been successfully updated.";
    }

    //Method to delete users
    public String deleteUser(UUID userid){
        Optional<Utilisateur> deletedUser = utilisateurRepository.findById(userid);
        if(deletedUser.isPresent()){
            utilisateurRepository.deleteById(deletedUser.get().getId());
            return "The user has been deleted.";
        } else {
            return "The specified user couldn't be deleted because it doesn't exist.";
        }
    }


}
