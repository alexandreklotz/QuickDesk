package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Team;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.TeamRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesklite.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private RolesRepository rolesRepository;
    private DeviceRepository deviceRepository;
    private TeamRepository teamRepository;

    @Autowired
    UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, DeviceRepository deviceRepository, TeamRepository teamRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamRepository = teamRepository;
        this.deviceRepository = deviceRepository;
        this.rolesRepository = rolesRepository;
    }


    ///////////////////////////////////////////
    // METHODS FOR PANEL AND BASIC FUNCTIONS //
    ///////////////////////////////////////////

    @Override
    public boolean isUserExisting(String login) {
        Optional<Utilisateur> searchedUser = utilisateurRepository.findUserWithLogin(login);
        if(searchedUser.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isUserAdmin(String login) {
        Optional<Utilisateur> searchedUser = utilisateurRepository.findUserWithLogin(login);
        if(searchedUser.isPresent() && searchedUser.get().getRole().equals("ADMIN")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getUserFullName(String login) {
        Optional<Utilisateur> searchedUser = utilisateurRepository.findUserWithLogin(login);
        String username = "";
        if(searchedUser.isPresent()){
            username = searchedUser.get().toString();
        }
        return username;
    }

    @Override
    public String getUserDevice(String login) {
        Optional<Utilisateur> currentUser = utilisateurRepository.findUserWithLogin(login);
        String deviceName = "";
        if(currentUser.isPresent()){
            if (currentUser.get().getDevice() != null) {
                Optional<Device> userDevice = deviceRepository.findById(currentUser.get().getDevice().getId());
                if (userDevice.isPresent()) {
                    deviceName = userDevice.get().getDeviceName();
                }
            } else {
                deviceName = "NoDeviceAssigned";
            }
        }
        return deviceName;
    }


    //////////////////////////////////////////////
    //              ADMIN METHODS               //
    //////////////////////////////////////////////

    @Override
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur getUserById(UUID userid) {
        Optional<Utilisateur> searchedUser = utilisateurRepository.findById(userid);
        if(searchedUser.isPresent()){
            return searchedUser.get();
        } else {
            return null; //Find a way to throw an exception
        }
    }

    @Override
    public Utilisateur getUserByLogin(String login) {
        Optional<Utilisateur> searchedUser = utilisateurRepository.findUserWithLogin(login);
        if(searchedUser.isPresent()){
            return searchedUser.get();
        } else {
            return null;
        }
    }

    @Override
    public Utilisateur createUser(Utilisateur utilisateur) {

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());

        if(existingUser){
            //return "This login is already assigned to an existing user.";
        } else {

            utilisateur.setUtilEnabled(true);
            utilisateur.setCreationDate(LocalDateTime.now());

            if(utilisateur.getUtilPwd() != null) {
                utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));
            } else {
                //return "Password cannot be empty.";
            }

            if(utilisateur.getRole() == null){
                Roles roleBdd = rolesRepository.getById(3L);
                utilisateur.setRole(roleBdd);
            } else if (utilisateur.getRole() != null){
                Optional<Roles> roleBdd = rolesRepository.findById(utilisateur.getRole().getId());
                if(roleBdd.isPresent()){
                    utilisateur.setRole(roleBdd.get());
                } else {
                    //return "You must assign a role to the user !"; //return error
                }
                utilisateurRepository.saveAndFlush(utilisateur);
            }
        }
        //return "User has been successfully created.";
        return utilisateur;
    }

    //TODO : Method below needs to return a message if it succeeded or not.
    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isPresent()){

            if(utilisateur.getRole() != null){
                Optional<Roles> newRole = rolesRepository.findById(utilisateur.getRole().getId());
                if(newRole.isPresent()){
                    updatedUser.get().setRole(newRole.get());
                }
            }
            if(utilisateur.getTeam() != null){
                Optional<Team> newTeam = teamRepository.findById(utilisateur.getTeam().getId());
                if(newTeam.isPresent()){
                    updatedUser.get().setTeam(newTeam.get());
                }
            }

            if(utilisateur.getUtilLogin() != null){
                boolean existingUser = isUserExisting(utilisateur.getUtilLogin());
                if(existingUser){
                    //return "The specified login is already assigned to another user.";
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
            //return "The user hasn't been found or couldn't be updated.";
        }
        //return "The specified user has been successfully updated.";
        return updatedUser.get();
    }

    @Override
    public Utilisateur updateUserRole(Utilisateur utilisateur) {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isPresent()){
            Optional<Roles> newRole = rolesRepository.findById(utilisateur.getRole().getId());
            if(newRole.isPresent()){
                updatedUser.get().setRole(newRole.get());
                utilisateurRepository.saveAndFlush(updatedUser.get());
                //return "The user's role has been updated.";
            } else {
                //return "The specified role doesn't exist, role hasn't been updated.";
            }
        }
        //return "Couldn't update the user's role.";
        return updatedUser.get();
    }

    @Override
    public Utilisateur updateUserTeam(Utilisateur utilisateur) {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isPresent()){
            Optional<Team> team = teamRepository.findById(utilisateur.getTeam().getId());
            if(team.isPresent()){
                updatedUser.get().setTeam(team.get());
                utilisateurRepository.saveAndFlush(updatedUser.get());
                //return "User's team updated.";
            } else {
                //return "The specified team doesn't exist, team hasn't been updated.";
            }
        }
        //return "Couldn't update the user's team";
        return updatedUser.get();
    }

    //TODO : Verify that it's coded properly.
    @Override
    public Utilisateur updateUserDevice(Utilisateur utilisateur) {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isPresent()){
            Optional<Device> newUserDevice = deviceRepository.findById(utilisateur.getDevice().getId());
            if(newUserDevice.isPresent()){
                updatedUser.get().setDevice(newUserDevice.get());
                utilisateurRepository.saveAndFlush(updatedUser.get());
                return updatedUser.get();
            } else {
                //return error, not existing device
            }
        }
        return utilisateur;
    }

    @Override
    public void deleteUser(UUID userid) {
        Optional<Utilisateur> deletedUser = utilisateurRepository.findById(userid);
        if(deletedUser.isPresent()){
            utilisateurRepository.delete(deletedUser.get());
        }
    }
}
