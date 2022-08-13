package fr.alexandreklotz.quickdesk.backend.service.implementation;

import fr.alexandreklotz.quickdesk.backend.error.DeviceException;
import fr.alexandreklotz.quickdesk.backend.error.TeamException;
import fr.alexandreklotz.quickdesk.backend.error.UtilisateurException;
import fr.alexandreklotz.quickdesk.backend.model.Device;
import fr.alexandreklotz.quickdesk.backend.model.Roles;
import fr.alexandreklotz.quickdesk.backend.model.Team;
import fr.alexandreklotz.quickdesk.backend.model.Utilisateur;
import fr.alexandreklotz.quickdesk.backend.repository.DeviceRepository;
import fr.alexandreklotz.quickdesk.backend.repository.RolesRepository;
import fr.alexandreklotz.quickdesk.backend.repository.TeamRepository;
import fr.alexandreklotz.quickdesk.backend.repository.UtilisateurRepository;
import fr.alexandreklotz.quickdesk.backend.service.UtilisateurService;
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


    ////////////////////////
    // FUNCTIONAL METHODS //
    ////////////////////////

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
        -> new UtilisateurException("ERROR : " + userid + " doesn't match any existing user."));
    }

    @Override
    public Utilisateur getUserByLogin(String login) throws UtilisateurException {
        return utilisateurRepository.findUserWithLogin(login).orElseThrow(()
        -> new UtilisateurException("ERROR : " + login + " doesn't match any existing user."));
    }

    @Override
    public Utilisateur createUser(Utilisateur utilisateur) throws UtilisateurException, TeamException, DeviceException {

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());

        if(existingUser){
            throw new UtilisateurException("ERROR : This user already exists.");
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

        if(utilisateur.getTeam() != null){
            Optional<Team> team = teamRepository.findById(utilisateur.getTeam().getId());
            if(team.isEmpty()){
                throw new TeamException("ERROR : The team assigned to the user " + utilisateur.getUtilLogin() + " doesn't exist.");
            }
        }

        if(utilisateur.getDevice() != null){
            Optional<Device> device = deviceRepository.findById(utilisateur.getDevice().getId());
            if(device.isEmpty()){
                throw new DeviceException("ERROR : The device " + utilisateur.getDevice().getId() + " doesn't exist and cannot be assigned to the user.");
            }
        }

        utilisateurRepository.saveAndFlush(utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) throws UtilisateurException, TeamException, DeviceException {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(updatedUser.isEmpty()){
            throw new UtilisateurException("ERROR : The user you're trying to update doesn't exist.");
        }

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());
        if(existingUser && !updatedUser.get().getUtilLogin().equals(utilisateur.getUtilLogin())){
            throw new UtilisateurException("ERROR : The login you specified is already used by another user => " + utilisateur.getUtilLogin());
        }

        if(utilisateur.getTeam() != null){
            Optional<Team> team = teamRepository.findById(utilisateur.getTeam().getId());
            if(team.isEmpty()){
                throw new TeamException("ERROR : The team you assigned to the user " + utilisateur.getUtilLogin() + " doesn't exist.");
            }
        }

        if(utilisateur.getDevice() != null){
            Optional<Device> device = deviceRepository.findById(utilisateur.getDevice().getId());
            if(device.isEmpty()){
                throw new DeviceException("ERROR : The device " + utilisateur.getDevice().getId() + " doesn't exist.");
            }
        }

        utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));

        utilisateurRepository.saveAndFlush(utilisateur);

        return utilisateur;
    }

    @Override
    public void deleteUserById(UUID userId) {
        utilisateurRepository.deleteById(userId);
    }
}
