package fr.alexandreklotz.quickdesklite.configuration;

import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import fr.alexandreklotz.quickdesklite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findUserWithLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username + " not found."));

        return new UserDetailsCustom(utilisateur);
    }
}
