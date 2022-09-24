package fr.alexandreklotz.quickdesk.security;

import fr.alexandreklotz.quickdesk.model.Utilisateur;
import fr.alexandreklotz.quickdesk.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    SecurityUserDetailsService(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository
                .findUserLoginAndRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("SECURITY ERROR : Cannot load user :" + username + ". User doesn't exist"));

                return new SecurityUserDetails(utilisateur);
    }

}