package fr.alexandreklotz.quickdesklite.service;


import fr.alexandreklotz.quickdesklite.error.DeviceException;
import fr.alexandreklotz.quickdesklite.error.RolesException;
import fr.alexandreklotz.quickdesklite.error.TeamException;
import fr.alexandreklotz.quickdesklite.error.UtilisateurException;
import fr.alexandreklotz.quickdesklite.model.Utilisateur;

import java.util.List;
import java.util.UUID;

public interface UtilisateurService {

    public boolean isUserExisting(String login);

    public boolean isUserAdmin(String login);

    public List<Utilisateur> getAllUsers();

    public Utilisateur getUserById(UUID userid) throws UtilisateurException;

    public Utilisateur getUserByLogin(String login) throws UtilisateurException;

    public Utilisateur createUser(Utilisateur utilisateur) throws UtilisateurException;

    public Utilisateur updateUser(Utilisateur utilisateur) throws UtilisateurException;

    public void deleteUser(Utilisateur utilisateur);
}
