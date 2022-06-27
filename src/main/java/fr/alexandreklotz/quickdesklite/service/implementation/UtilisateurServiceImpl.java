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

    @Override
    public String getUserFullName(String login) throws UtilisateurException {
        Optional<Utilisateur> searchedUser = utilisateurRepository.findUserWithLogin(login);
        if(!searchedUser.isPresent()){
            throw new UtilisateurException(login + " doesn't match any existing user.");
        }
        return searchedUser.get().toString();
    }

    /*@Override
    public String getUserDevice(String login) throws UtilisateurException, DeviceException {
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
    }*/

    @Override
    public String getUserDevice(String login) throws UtilisateurException, DeviceException {

        Optional<Utilisateur> currentUser = utilisateurRepository.findUserWithLogin(login);
        String deviceName = "";
        if(!currentUser.isPresent()){
            throw new UtilisateurException(login + " doesn't match any existing user.");
        }

        deviceName = "NoDeviceAssigned";

        if(currentUser.get().getDevice() != null){
            Optional<Device> userDevice = deviceRepository.findById(currentUser.get().getDevice().getId());
            if(!userDevice.isPresent()){
                throw new DeviceException("The device with the id " + currentUser.get().getDevice().getId() + " currently linked to the user " + currentUser.get().getUtilLogin() + " doesn't exist.");
            }
            deviceName = userDevice.get().getDeviceName();
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
    public Utilisateur createUser(Utilisateur utilisateur) throws UtilisateurException, DeviceException ,TeamException, RolesException {

        boolean existingUser = isUserExisting(utilisateur.getUtilLogin());

        if(existingUser){
            throw new UtilisateurException("This user already exists.");
        }

        utilisateur.setUtilEnabled(true);
        utilisateur.setCreationDate(LocalDateTime.now());


        if(utilisateur.getUtilPwd() == null){
            throw new UtilisateurException("The password cannot be empty. User couldn't be created.");
        }

        utilisateur.setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));

        if(utilisateur.getRole() == null){
            Roles roleBdd = rolesRepository.getById(3L);
            utilisateur.setRole(roleBdd);
        }

        Optional<Roles> roleBdd = rolesRepository.findById(utilisateur.getRole().getId());
        if(!roleBdd.isPresent()){
            throw new RolesException("The role has either not been specified during the user creation process or doesn't exist.");
        }
        utilisateur.setRole(roleBdd.get());

        if(utilisateur.getTeam() != null){
            Optional<Team> userTeam = teamRepository.findById(utilisateur.getTeam().getId());
            if(!userTeam.isPresent()){
                throw new TeamException("The team assigned to the user doesn't exist.");
            }
            utilisateur.setTeam(userTeam.get());
        }

        if(utilisateur.getDevice() != null){
            Optional<Device> userDevice = deviceRepository.findById(utilisateur.getDevice().getId());
            if(!userDevice.isPresent()){
                throw new DeviceException("The device assigned to the user doesn't exist.");
            }
            utilisateur.setDevice(userDevice.get());
        }

        utilisateurRepository.saveAndFlush(utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) throws UtilisateurException, DeviceException, TeamException, RolesException {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(!updatedUser.isPresent()){
            throw new UtilisateurException("The user you're trying to update doesn't exist.");
        }

        Optional<Team> userTeam = teamRepository.findById(utilisateur.getTeam().getId());
        if(!userTeam.isPresent()){
            throw new TeamException("The team you specified during the user creation process doesn't exist.");
        }
        updatedUser.get().setTeam(userTeam.get());

        Optional<Device> userDevice = deviceRepository.findById(utilisateur.getDevice().getId());
        if(!userDevice.isPresent()){
            throw new DeviceException("The device assigned to the user doesn't exist.");
        }
        updatedUser.get().setDevice(userDevice.get());

        if(utilisateur.getRole() != null){
            Optional<Roles> userRole = rolesRepository.findById(utilisateur.getRole().getId());
            if(!userRole.isPresent()){
                throw new RolesException("The specified role doesn't exist.");
            }
            updatedUser.get().setRole(userRole.get());
        }

        if(utilisateur.getUtilLogin() != null){
            boolean existingUser = isUserExisting(utilisateur.getUtilLogin());
            if(existingUser){
                throw new UtilisateurException("The login you specified is already used by another user.");
            }
            updatedUser.get().setUtilLogin(utilisateur.getUtilLogin());
        }

        if(utilisateur.getUtilPwd() != null){
            updatedUser.get().setUtilPwd(passwordEncoder.encode(utilisateur.getUtilPwd()));
        }

        if(utilisateur.getUtilFirstName() != null){
            updatedUser.get().setUtilFirstName(utilisateur.getUtilFirstName());
        }

        if(utilisateur.getUtilLastName() != null){
            updatedUser.get().setUtilLastName(utilisateur.getUtilLastName());
        }

        if(utilisateur.getUtilMailAddr() != null){
            updatedUser.get().setUtilMailAddr(utilisateur.getUtilMailAddr());
        }

        utilisateurRepository.saveAndFlush(updatedUser.get());
        return updatedUser.get();
    }

    @Override
    public Utilisateur updateUserRole(Utilisateur utilisateur) throws UtilisateurException, RolesException {
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(!updatedUser.isPresent()){
            throw new UtilisateurException("The user you're trying to update doesn't exist.");
        }

        Optional<Roles> userRole = rolesRepository.findById(utilisateur.getRole().getId());
        if(!userRole.isPresent()){
            throw new RolesException("The role you're trying to assign to the user doesn't exist.");
        }
        updatedUser.get().setRole(userRole.get());
        utilisateurRepository.saveAndFlush(updatedUser.get());
        return updatedUser.get();
    }

    @Override
    public Utilisateur updateUserTeam(Utilisateur utilisateur) throws UtilisateurException, TeamException{
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(!updatedUser.isPresent()){
            throw new UtilisateurException("The user you're trying to update doesn't exist.");
        }

        Optional<Team> userTeam = teamRepository.findById(utilisateur.getTeam().getId());
        if(!userTeam.isPresent()){
            throw new TeamException("The team you're trying to assign to the user doesn't exist.");
        }

        updatedUser.get().setTeam(userTeam.get());
        utilisateurRepository.saveAndFlush(updatedUser.get());
        return updatedUser.get();
    }


    @Override
    public Utilisateur updateUserDevice(Utilisateur utilisateur) throws UtilisateurException, DeviceException{
        Optional<Utilisateur> updatedUser = utilisateurRepository.findById(utilisateur.getId());
        if(!updatedUser.isPresent()){
            throw new UtilisateurException("The user you're trying to update doesn't exist.");
        }

        Optional<Device> userDevice = deviceRepository.findById(utilisateur.getDevice().getId());
        if(!userDevice.isPresent()){
            throw new DeviceException("The device you're trying to assign to the user doesn't exist.");
        }

        updatedUser.get().setDevice(userDevice.get());
        utilisateurRepository.saveAndFlush(updatedUser.get());
        return updatedUser.get();
    }

    @Override
    public Utilisateur disableUser(Utilisateur utilisateur) throws UtilisateurException {
        Optional<Utilisateur> disabledUser = utilisateurRepository.findById(utilisateur.getId());
        if(!disabledUser.isPresent()){
            throw new UtilisateurException("The user you're trying to disable doesn't exist.");
        }
        disabledUser.get().setUtilEnabled(false);
        utilisateurRepository.saveAndFlush(disabledUser.get());
        return disabledUser.get();
    }

    @Override
    public void deleteUser(Utilisateur utilisateur) {
        utilisateurRepository.delete(utilisateur);
    }
}
