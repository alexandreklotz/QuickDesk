package fr.alexandreklotz.quickdesklite.service.implementation;

import fr.alexandreklotz.quickdesklite.error.DeviceException;
import fr.alexandreklotz.quickdesklite.error.RolesException;
import fr.alexandreklotz.quickdesklite.error.TeamException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
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

    //////////////////////////////////////////////
    //              ADMIN METHODS               //
    //////////////////////////////////////////////

    @Override
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur getUserById(UUID userid) throws UtilisateurException {
        return utilisateurRepository.findById(userid).orElseThrow(()
        -> new UtilisateurException(userid + " doesn't match any existing user."));
    }

    @Override
    public Utilisateur getUserByLogin(String login) throws UtilisateurException {
        return utilisateurRepository.findUserWithLogin(login).orElseThrow(()
        -> new UtilisateurException(login + " doesn't match any existing user."));
    }

    @Override
    public Utilisateur createUser(Utilisateur utilisateur) throws UtilisateurException {

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());

        if(existingUser){
            throw new UtilisateurException("This user already exists.");
        }

        utilisateur.setUtilEnabled(true);
        utilisateur.setCreationDate(LocalDateTime.now());


        if(utilisateur.getUtilPwd() == null){
            throw new UtilisateurException("ERROR : User " + utilisateur.getUtilLogin() + " couldn't be created. Password cannot be empty");
        }

        utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));

        if(utilisateur.getRole() == null){
            Roles roleBdd = rolesRepository.getById(3L);
            utilisateur.setRole(roleBdd);
        }

        utilisateurRepository.saveAndFlush(utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) throws UtilisateurException {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isEmpty()){
            throw new UtilisateurException("The user you're trying to update doesn't exist.");
        }

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());
        if(existingUser){
            throw new UtilisateurException("ERROR : The login you specified is already used by another user => " + utilisateur.getUtilLogin());
        }

        utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));

        //utilisateurRepository.delete(updatedUser.get());
        utilisateurRepository.saveAndFlush(utilisateur);

        return utilisateur;
    }

    @Override
    public void deleteUser(Utilisateur utilisateur) {
        utilisateurRepository.delete(utilisateur);
    }
}
