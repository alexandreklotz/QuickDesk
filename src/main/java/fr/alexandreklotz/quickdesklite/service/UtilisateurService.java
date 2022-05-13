package fr.alexandreklotz.quickdesklite.service;


import fr.alexandreklotz.quickdesklite.model.Utilisateur;

import java.util.List;
import java.util.UUID;

public interface UtilisateurService {

    public boolean isUserExisting(String login);

    public boolean isUserAdmin(String login);

    public String getUserFullName(String login);

    public String getUserDevice(String login);

    public List<Utilisateur> getAllUsers();

    public Utilisateur getUserById(UUID userid);

    public Utilisateur getUserByLogin(String login);

    public Utilisateur createUser(Utilisateur utilisateur);

    public Utilisateur updateUser(Utilisateur utilisateur);

    public String deleteUser(UUID userid);

    public Utilisateur updateUserRole(Utilisateur utilisateur);

    public Utilisateur updateUserTeam(Utilisateur utilisateur);

    public Utilisateur updateUserDevice(Utilisateur utilisateur);
}
