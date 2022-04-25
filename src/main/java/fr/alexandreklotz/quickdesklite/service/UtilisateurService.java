package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;
    private DeviceRepository deviceRepository;

    @Autowired
    UtilisateurService(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository, DeviceRepository deviceRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
        this.deviceRepository = deviceRepository;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //This service will be used to retrieve the users' info such as roles, name, etc...//
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
}
