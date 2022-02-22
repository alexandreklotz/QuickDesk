package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Roles;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.RolesRepository;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;


@RestController
@CrossOrigin
public class LoginController {

    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;

    @Autowired
    LoginController(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
    }

    ////////////////
    //Rest Methods//
    ////////////////

    @GetMapping("/redirection")
    @ResponseBody
    public void loggedUserRedirection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Principal principal = request.getUserPrincipal();
        String userlogin = principal.getName();
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(userlogin);
        if(userBdd.isPresent()){
            Long roleId = userBdd.get().getRole().getId();
            Optional<Roles> roleBdd = rolesRepository.findById(roleId);
            if(roleBdd.isPresent()){
                if(Objects.equals(roleBdd.get().getRoleName(), "USER")) {
                    String redirectUsr = "/homepage/" + userlogin;
                    response.sendRedirect(redirectUsr);
                } else if (Objects.equals(roleBdd.get().getRoleName(), "VIP")){
                    String redirectVip = "/homepage/" + userlogin;
                    response.sendRedirect(redirectVip);
                } else if (Objects.equals(roleBdd.get().getRoleName(), "ADMIN")){
                    String redirectAdm = "/admin/" + userlogin;
                    response.sendRedirect(redirectAdm);
                }
            }
        }
    }

}
