package fr.alexandreklotz.quickdesklite.controller;

import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin
public class RedirectionController {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    RedirectionController(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/mypanel")
    @ResponseBody
    public void panelRedirect (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Optional<Utilisateur> userBdd = utilisateurRepository.findUserWithLogin(username);
        if(userBdd.isPresent()) {
            response.sendRedirect("/mypanel/" + username);
        }
    }
}
