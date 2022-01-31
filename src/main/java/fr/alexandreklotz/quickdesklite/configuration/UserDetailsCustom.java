package fr.alexandreklotz.quickdesklite.configuration;

import fr.alexandreklotz.quickdesklite.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserDetailsCustom implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public UserDetailsCustom(Utilisateur utilisateur){
        this.id = utilisateur.getId();
        this.username = utilisateur.getUtilLogin();
        this.password = utilisateur.getUtilPwd();
        this.active = utilisateur.isUtilEnabled();

        authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(utilisateur.getRole().getRoleName()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }
}
