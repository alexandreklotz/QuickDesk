package fr.alexandreklotz.quickdesklite.service;

import fr.alexandreklotz.quickdesklite.model.Device;
import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Ticket;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.DeviceRepository;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.TicketRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class MyPanelService {

    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;
    private TicketRepository ticketRepository;
    private DeviceRepository deviceRepository;
    private TicketService ticketService;

    @Autowired
    MyPanelService(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository, TicketRepository ticketRepository, DeviceRepository deviceRepository, TicketService ticketService){
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
        this.ticketRepository = ticketRepository;
        this.deviceRepository = deviceRepository;
        this.ticketService = ticketService;
    }

    /* This method is used by the PanelController. The URL to the user's panel is mypanel/login. Everytime the user tries to access the /panel link,
    this service will be called by the controller with the login used in the URL and the user's login from his HttpResponse. It will compare both,
    if both are identical then the user is trying to access his own panel which is fine but if they differ it means that he tried to access another
    user's panel.*/
    public boolean userPanelVerification (String login, String requestLogin){
        UUID calledUserId = null;
        UUID callingUserId = null;

        Optional<Utilisateur> calledUser = utilisateurRepository.findUserWithLogin(login);
        if(calledUser.isPresent()){
            calledUserId = calledUser.get().getId();
        }
        Optional<Utilisateur> callingUser = utilisateurRepository.findUserWithLogin(requestLogin);
        if(callingUser.isPresent()){
            callingUserId = callingUser.get().getId();
        }
        if(Objects.equals(calledUserId, callingUserId)){
            return true;
        } else {
            return false;
        }
    }

    //This method checks if the user trying to access his panel is an admin, if yes we return "TRUE", if not we return "FALSE".
    public boolean isUserAdmin (String login) {
        String roleName = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            Optional<Roles> roleBdd = rolesRepository.findById(userBdd.get().getRole().getId());
            if (roleBdd.isPresent()){
                roleName = roleBdd.get().getRoleName();
            }
        }
        if(roleName.equals("ADMIN")){
            return true;
        } else {
            return false;
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //The two methods below are used to retrieve basic info about the user.//
    /////////////////////////////////////////////////////////////////////////

    public String getUserFullName (String login) {
        String userName = null;
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(login);
        if(userBdd.isPresent()){
            userName = userBdd.get().getUtilFirstName() + " " + userBdd.get().getUtilLastName();
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
